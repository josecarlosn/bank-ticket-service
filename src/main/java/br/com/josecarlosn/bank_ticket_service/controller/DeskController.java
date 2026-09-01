package br.com.josecarlosn.bank_ticket_service.controller;

import br.com.josecarlosn.bank_ticket_service.DTO.response.DeskResponseDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Desk;
import br.com.josecarlosn.bank_ticket_service.repository.DeskRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("desk")
public class DeskController {
    private final DeskRepository repository;
    public DeskController(DeskRepository repository){
        this.repository = repository;
    }
    @GetMapping
    public List<DeskResponseDTO> listAll(){
        return repository.findAll().stream().map(DeskResponseDTO::new).toList();
    }
}
