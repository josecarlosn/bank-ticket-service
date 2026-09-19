package br.com.josecarlosn.bank_ticket_service.service;

import br.com.josecarlosn.bank_ticket_service.dto.request.DepartmentRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.DepartmentResponseDTO;
import br.com.josecarlosn.bank_ticket_service.dto.update.DepartmentUpdateDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.exceptions.InvalidDepartmentException;
import br.com.josecarlosn.bank_ticket_service.repository.DepartmentRepository;
import br.com.josecarlosn.bank_ticket_service.repository.DeskRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository repository;
    private final DeskRepository deskRepository;

    public DepartmentService(DepartmentRepository repository, DeskRepository deskRepository){this.repository = repository;
        this.deskRepository = deskRepository;
    }

    public List<DepartmentResponseDTO> listActiveDepartment(){
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        return repository.findByIsActiveTrue(sort).stream().map(DepartmentResponseDTO::new).toList();
    }
    public List<DepartmentResponseDTO> listAllDepartments(){
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        return repository.findAll(sort).stream().map(DepartmentResponseDTO :: new).toList();
    }

    public List<DepartmentResponseDTO> create(DepartmentRequestDTO dto){
        if(repository.existsByName(dto.name())){throw new InvalidDepartmentException("Department's name already exists!");}
        if(repository.existsByTag(dto.tag())){throw new InvalidDepartmentException("Department's tag already exists!");}
        if(repository.existsByPriorityTag(dto.priorityTag())){throw new InvalidDepartmentException("Department's priority tag already exists!");}

        Department department = new Department(dto.name(), dto.tag(), dto.priorityTag());

        repository.save(department);
        return listActiveDepartment();
    }

    @Transactional
    public DepartmentResponseDTO update(Integer id, DepartmentUpdateDTO dto) {
        Department department = repository.findById(id).orElseThrow(() -> new InvalidDepartmentException("Department not found!"));
        if (dto.name() != null) {
            department.setName(dto.name());
        }
        if (dto.tag() != null) {
            department.setTag(dto.tag());
        }
        if (dto.priorityTag() != null) {
            department.setPriorityTag(dto.priorityTag());
        }
        return new DepartmentResponseDTO(department);
    }

    public void delete(Integer id){
        if(!repository.existsById(id)){
            throw new InvalidDepartmentException("Department doesn't exist.");
        }
        if(deskRepository.existsByDepartmentId(id)){
            throw new InvalidDepartmentException("Cannot delete a department that has desks linked to it.");
        }

        repository.deleteById(id);
    }
    public void activate(Integer id){
        Department department = repository.findById(id).orElseThrow(() -> new InvalidDepartmentException("Department not found."));
        department.activate();
        repository.save(department);
    }
    public void deactivate(Integer id){
        Department department = repository.findById(id).orElseThrow(() -> new InvalidDepartmentException("Department not found."));
        department.deactivate();
        repository.save(department);
    }

}
