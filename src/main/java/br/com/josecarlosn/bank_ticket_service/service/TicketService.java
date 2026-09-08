package br.com.josecarlosn.bank_ticket_service.service;

import br.com.josecarlosn.bank_ticket_service.dto.response.TicketResponseDTO;
import br.com.josecarlosn.bank_ticket_service.repository.TicketRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository repository;
    public TicketService(TicketRepository repository){this.repository = repository;}

    public List<TicketResponseDTO> list(){
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        return repository.findAll(sort).stream().map(TicketResponseDTO::new).toList();
    }
}
