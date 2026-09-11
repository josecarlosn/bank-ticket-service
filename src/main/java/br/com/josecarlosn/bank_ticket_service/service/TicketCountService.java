package br.com.josecarlosn.bank_ticket_service.service;


import br.com.josecarlosn.bank_ticket_service.dto.request.TicketCountRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.request.TicketRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.TicketCountResponseDTO;
import br.com.josecarlosn.bank_ticket_service.entity.TicketCount;
import br.com.josecarlosn.bank_ticket_service.exceptions.TicketCountException;
import br.com.josecarlosn.bank_ticket_service.repository.DepartmentRepository;
import br.com.josecarlosn.bank_ticket_service.repository.TicketCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketCountService {
    private final TicketCountRepository ticketCountRepository;
    private final DepartmentRepository departmentRepository;

    public List<TicketCountResponseDTO> list(){
        Sort sort = Sort.by(Sort.Direction.ASC, "Date").and(Sort.by(Sort.Direction.ASC,"departmentId"));
        return ticketCountRepository.findAll(sort).stream().map(TicketCountResponseDTO::new).toList();
    }

    public TicketCount create(TicketCountRequestDTO dto){
        LocalDate today = LocalDate.now();
        if(dto.date().isAfter(today)){
            throw new TicketCountException("Date can't be later than today");
        }
        if(!departmentRepository.existsById(dto.departmentId())){
           throw new TicketCountException("Department doesn't exist.");
        }
        if(ticketCountRepository.existsByDepartmentIdAndHavePriorityAndDate(dto.departmentId(),dto.havePriority(),dto.date())){
            throw new TicketCountException("TicketCount already exists.");
        }
        TicketCount ticketCount = new TicketCount(dto.departmentId(), dto.havePriority(), dto.date());
        return  ticketCountRepository.save(ticketCount);
    }

    public Integer nextTicketNumber(TicketRequestDTO dto){
        LocalDate today = LocalDate.now();
        Optional<TicketCount> ticketCount = ticketCountRepository.findByDepartmentIdAndHavePriorityAndDate(dto.departmentId(), dto.havePriority(), today);
        return ticketCount.map(count -> {return count.getLastNumber() + 1;}).orElseGet(() -> {return 1;});


    }
}
