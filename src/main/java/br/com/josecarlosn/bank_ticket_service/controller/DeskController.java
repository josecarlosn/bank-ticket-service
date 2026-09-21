package br.com.josecarlosn.bank_ticket_service.controller;

import br.com.josecarlosn.bank_ticket_service.dto.request.DeskRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.DeskResponseDTO;
import br.com.josecarlosn.bank_ticket_service.dto.update.DeskUpdateDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Desk;
import br.com.josecarlosn.bank_ticket_service.infra.RestExceptionMessage;
import br.com.josecarlosn.bank_ticket_service.service.DeskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("desk")
public class DeskController {
    private final DeskService service;

    public DeskController(DeskService service) {
        this.service = service;
    }

    @GetMapping
    public List<DeskResponseDTO> listAll() {
        return service.list();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DeskRequestDTO dto) {
        service.create(dto);
        return ResponseEntity.ok("Desk created!");
    }
    @PostMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivate(@PathVariable Integer id) {
        service.deactivate(id);
        return ResponseEntity.ok("Desk deactivate.");
    }
    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activate(@PathVariable Integer id) {
        service.activate(id);
        return ResponseEntity.ok("Desk activate.");
    }
    @PatchMapping("/update/{id}")
    public DeskResponseDTO update(@PathVariable Integer id,@Valid @RequestBody DeskUpdateDTO dto){
        return service.update(id, dto);
    }
}