package br.com.josecarlosn.bank_ticket_service.controller;

import br.com.josecarlosn.bank_ticket_service.dto.request.DepartmentRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.DepartmentResponseDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("department")
public class DepartmentController {
    private final DepartmentService service;
    public DepartmentController(DepartmentService service){this.service = service;}

    @GetMapping
    public List<DepartmentResponseDTO> listDepartments(){
        return service.listDepartment();
    }

    @PostMapping
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartmentRequestDTO dto){
        Department department = new Department(dto);
        service.create(department);
        return ResponseEntity.ok("Department created successfully");
    }
}
