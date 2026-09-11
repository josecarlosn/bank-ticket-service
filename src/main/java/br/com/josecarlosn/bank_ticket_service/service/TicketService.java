package br.com.josecarlosn.bank_ticket_service.service;

import br.com.josecarlosn.bank_ticket_service.dto.request.TicketCountRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.request.TicketRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.TicketResponseDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.entity.Ticket;
import br.com.josecarlosn.bank_ticket_service.exceptions.TicketException;
import br.com.josecarlosn.bank_ticket_service.repository.DepartmentRepository;
import br.com.josecarlosn.bank_ticket_service.repository.TicketRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final DepartmentRepository departmentRepository;
    private final TicketCountService ticketCountService;


    public List<TicketResponseDTO> list(){
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        return ticketRepository.findAll(sort).stream().map(TicketResponseDTO::new).toList();
    }
    @Transactional
    public List<TicketResponseDTO> generateTicket(TicketRequestDTO dto){
        int nextNumber = ticketCountService.nextTicketNumber(dto);
        Department department = departmentRepository.findById(dto.departmentId()).orElseThrow(() -> new TicketException("DepartmentId not found"));
        LocalDateTime today = LocalDateTime.now();
        TicketCountRequestDTO tcRequestDTO = new TicketCountRequestDTO(dto.departmentId(), dto.havePriority(), today.toLocalDate());

        ticketCountService.create(tcRequestDTO);

        Ticket ticket = new Ticket(department, dto.havePriority(),nextNumber, today);
        ticketRepository.save(ticket);
        buildTicketCode(ticket);
        return list();
    }

    public String buildTicketCode(Ticket ticket){
        String tag = ticket.isHavePriority() ? ticket.getDepartment().getPriorityTag() : ticket.getDepartment().getTag();
        return tag + ticket.getNumber();
    }



}
