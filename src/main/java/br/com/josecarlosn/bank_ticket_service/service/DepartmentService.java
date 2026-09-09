package br.com.josecarlosn.bank_ticket_service.service;

import br.com.josecarlosn.bank_ticket_service.dto.request.DepartmentRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.DepartmentResponseDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.exceptions.InvalidDepartmentException;
import br.com.josecarlosn.bank_ticket_service.repository.DepartmentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository repository;
    public DepartmentService(DepartmentRepository repository){this.repository = repository;}

    public List<DepartmentResponseDTO> listDepartment(){
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        return repository.findAll(sort).stream().map(DepartmentResponseDTO::new).toList();
    }

    public List<DepartmentResponseDTO> create(DepartmentRequestDTO dto){
        if(repository.existsByName(dto.name())){throw new InvalidDepartmentException("Department's name already exists!");}
        if(repository.existsByTag(dto.tag())){throw new InvalidDepartmentException("Department's tag already exists!");}
        if(repository.existsByPriorityTag(dto.priorityTag())){throw new InvalidDepartmentException("Department's priority tag already exists!");}

        Department department = new Department(dto);


        repository.save(department);
        return listDepartment();
    }


}
