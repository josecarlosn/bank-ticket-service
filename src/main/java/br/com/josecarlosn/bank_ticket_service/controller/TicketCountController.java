package br.com.josecarlosn.bank_ticket_service.controller;

import br.com.josecarlosn.bank_ticket_service.dto.request.TicketCountRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.TicketCountResponseDTO;
import br.com.josecarlosn.bank_ticket_service.entity.TicketCount;
import br.com.josecarlosn.bank_ticket_service.service.TicketCountService;
import br.com.josecarlosn.bank_ticket_service.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("ticket-count")
public class TicketCountController {
    private final TicketCountService service;
    public TicketCountController(TicketCountService service){this.service = service;}

    @GetMapping
    public List<TicketCountResponseDTO> list(){
        return service.list();
    }

    @PostMapping
    public TicketCount create(@RequestBody TicketCountRequestDTO dto){
        return service.create(dto);
    }
}
