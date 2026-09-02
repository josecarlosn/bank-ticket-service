package br.com.josecarlosn.bank_ticket_service.controller;

import br.com.josecarlosn.bank_ticket_service.DTO.response.DeskResponseDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Desk;
import br.com.josecarlosn.bank_ticket_service.repository.DeskRepository;
import br.com.josecarlosn.bank_ticket_service.service.DeskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
