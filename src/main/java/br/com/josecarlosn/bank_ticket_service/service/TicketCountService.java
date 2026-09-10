package br.com.josecarlosn.bank_ticket_service.service;


import br.com.josecarlosn.bank_ticket_service.dto.response.TicketCountResponseDTO;
import br.com.josecarlosn.bank_ticket_service.repository.TicketCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketCountService {
    private final TicketCountRepository repository;

    public List<TicketCountResponseDTO> list(){
        Sort sort = Sort.by(Sort.Direction.ASC, "Date").and(Sort.by(Sort.Direction.ASC,"departmentId"));
        return repository.findAll(sort).stream().map(TicketCountResponseDTO::new).toList();
    }

}
