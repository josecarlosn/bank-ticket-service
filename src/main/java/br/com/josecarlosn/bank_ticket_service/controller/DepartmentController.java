package br.com.josecarlosn.bank_ticket_service.controller;

import br.com.josecarlosn.bank_ticket_service.repository.DepartmentRepository;
import br.com.josecarlosn.bank_ticket_service.DTO.response.DepartmentResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("department")
public class DepartmentController {
//    @Autowired
//    DepartmentRepository repository;
    private final DepartmentRepository repository;
    public DepartmentController(DepartmentRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<DepartmentResponseDTO> listAll() {
        return repository.findAll().stream().map(DepartmentResponseDTO::new).toList();
    };
}
