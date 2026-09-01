package br.com.josecarlosn.bank_ticket_service.controller;

import br.com.josecarlosn.bank_ticket_service.DTO.response.TicketResponseDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Ticket;
import br.com.josecarlosn.bank_ticket_service.repository.TicketRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("ticket")
public class TicketController {
    private final TicketRepository repository;
    public TicketController(TicketRepository repository){
      this.repository = repository;
    }
    @GetMapping
    public List<TicketResponseDTO> listAll(){
      return repository.findAll().stream().map(TicketResponseDTO::new).toList();
    };

}
