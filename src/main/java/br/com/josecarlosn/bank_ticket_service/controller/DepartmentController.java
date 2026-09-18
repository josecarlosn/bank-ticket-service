package br.com.josecarlosn.bank_ticket_service.controller;

import br.com.josecarlosn.bank_ticket_service.dto.request.DepartmentRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.DepartmentResponseDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.infra.RestExceptionMessage;
import br.com.josecarlosn.bank_ticket_service.service.DepartmentService;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
        service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RestExceptionMessage(HttpStatus.CREATED, "Department created"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new RestExceptionMessage(HttpStatus.OK, "Department deleted!"));
    }
}
