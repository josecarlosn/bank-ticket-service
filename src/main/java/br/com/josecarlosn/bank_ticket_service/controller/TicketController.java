package br.com.josecarlosn.bank_ticket_service.controller;

import br.com.josecarlosn.bank_ticket_service.DTO.response.TicketResponseDTO;
import br.com.josecarlosn.bank_ticket_service.service.TicketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("ticket")
public class TicketController {
    private final TicketService service;
    public TicketController(TicketService service){this.service = service;}

    @GetMapping
    public List<TicketResponseDTO> listAll(){
      return service.list();
    };

}
