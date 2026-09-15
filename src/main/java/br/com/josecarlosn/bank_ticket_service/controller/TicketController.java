package br.com.josecarlosn.bank_ticket_service.controller;

import br.com.josecarlosn.bank_ticket_service.dto.request.DeskRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.request.TicketActionRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.request.TicketRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.TicketActionResponseDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.TicketResponseDTO;
import br.com.josecarlosn.bank_ticket_service.infra.RestExceptionMessage;
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
    public ResponseEntity<RestExceptionMessage> call(@PathVariable Long id, @RequestBody TicketActionRequestDTO dto){
        TicketActionResponseDTO response = service.call(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RestExceptionMessage(HttpStatus.CREATED, "Ticket created!"));
    }
    @PostMapping("finish/{id}")
    public ResponseEntity<RestExceptionMessage> finish(@PathVariable Long id){
        service.finish(id);
        return ResponseEntity.status(HttpStatus.OK).body(new RestExceptionMessage(HttpStatus.OK, "Ticket finished!"));    }
    @PostMapping("cancel/{id}")
    public ResponseEntity<RestExceptionMessage> cancel(@PathVariable Long id){
        service.cancel(id);
        return ResponseEntity.status(HttpStatus.OK).body(new RestExceptionMessage(HttpStatus.OK, "Ticket canceled!"));
    }
}
