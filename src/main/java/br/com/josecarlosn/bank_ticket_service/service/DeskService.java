package br.com.josecarlosn.bank_ticket_service.service;

import br.com.josecarlosn.bank_ticket_service.dto.request.DeskRequestDTO;
import br.com.josecarlosn.bank_ticket_service.dto.response.DeskResponseDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.entity.Desk;
import br.com.josecarlosn.bank_ticket_service.exceptions.InvalidDeskException;
import br.com.josecarlosn.bank_ticket_service.repository.DepartmentRepository;
import br.com.josecarlosn.bank_ticket_service.repository.DeskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeskService {
    private final DeskRepository deskRepository;
    private final DepartmentRepository departmentRepository;
    public List<DeskResponseDTO> list(){
        Sort sort = Sort.by(Sort.Direction.ASC, "department_id")
                .and(Sort.by(Sort.Direction.ASC, "number"));
        return deskRepository.findAll(sort).stream().map(DeskResponseDTO::new).toList();
    }

    public List<DeskResponseDTO> create(DeskRequestDTO dto){
        if(deskRepository.existsByDepartmentIdAndNumber(dto.departmentId(), dto.number())){
            throw new InvalidDeskException("Desk already exists.");
        }
        if (!departmentRepository.existsById(dto.departmentId())){
            throw new InvalidDeskException("Department not found.");
        }

        Department department = departmentRepository.findById(dto.departmentId())
                .orElseThrow(() -> (new RuntimeException("Department not found.")));
        Desk desk = new Desk(department, dto.number());
        deskRepository.save(desk);
        return list();
    }
}
