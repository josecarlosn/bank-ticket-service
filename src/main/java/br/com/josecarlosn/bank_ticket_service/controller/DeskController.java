package br.com.josecarlosn.bank_ticket_service.controller;

import br.com.josecarlosn.bank_ticket_service.dto.request.DeskRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.DeskResponseDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Desk;
import br.com.josecarlosn.bank_ticket_service.service.DeskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("desk")
public class DeskController {
    private final DeskService service;
    public DeskController(DeskService service){
        this.service = service;
    }
    @GetMapping
    public List<DeskResponseDTO> listAll(){
        return service.list();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DeskRequestDTO dto){
        Desk desk = new Desk(dto);
        service.create(desk);
        return ResponseEntity.ok("Desk created! ");
    }

}
