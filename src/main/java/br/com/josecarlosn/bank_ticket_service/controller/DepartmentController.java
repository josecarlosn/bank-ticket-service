package br.com.josecarlosn.bank_ticket_service.controller;

import br.com.josecarlosn.bank_ticket_service.DTO.response.DepartmentResponseDTO;
import br.com.josecarlosn.bank_ticket_service.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("department")
public class DepartmentController {
    private final DepartmentService service;
    public DepartmentController(DepartmentService service){this.service = service;}

    @GetMapping
    public List<DepartmentResponseDTO> listDepartments(){
        return service.list();
    }
}
