package br.com.josecarlosn.bank_ticket_service.service;

import br.com.josecarlosn.bank_ticket_service.DTO.response.DepartmentResponseDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.repository.DepartmentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository repository;
    public DepartmentService(DepartmentRepository repository){this.repository = repository;}

    public List<DepartmentResponseDTO> list(){
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        return repository.findAll(sort).stream().map(DepartmentResponseDTO::new).toList();
    }


}
