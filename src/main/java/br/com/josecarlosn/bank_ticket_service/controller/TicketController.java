package br.com.josecarlosn.bank_ticket_service.controller;

import br.com.josecarlosn.bank_ticket_service.dto.request.TicketActionRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.request.TicketRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.TicketActionResponseDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.TicketResponseDTO;
import br.com.josecarlosn.bank_ticket_service.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ticket")
public class TicketController {
    private final TicketService service;
    public TicketController(TicketService service){this.service = service;}

    @GetMapping
    public List<TicketResponseDTO> list(){
      return service.list();
    };
    @PostMapping
    public List<TicketResponseDTO> generateTicket(@RequestBody TicketRequestDTO dto){
        return service.generateTicket(dto);
    }
    @PostMapping("call/{id}")
    public ResponseEntity<?> call(@PathVariable Long id, @RequestBody int deskId){
        return ResponseEntity.ok(service.call(id, deskId));
    }
}
