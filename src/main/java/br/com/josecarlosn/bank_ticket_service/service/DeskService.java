package br.com.josecarlosn.bank_ticket_service.service;

import br.com.josecarlosn.bank_ticket_service.dto.response.DeskResponseDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Desk;
import br.com.josecarlosn.bank_ticket_service.exceptions.InvalidDeskException;
import br.com.josecarlosn.bank_ticket_service.infra.RestExceptionMessage;
import br.com.josecarlosn.bank_ticket_service.repository.DeskRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeskService {
    private final DeskRepository repository;
    public DeskService(DeskRepository repository){
        this.repository = repository;
    }

    public List<DeskResponseDTO> list(){
        Sort sort = Sort.by(Sort.Direction.ASC, "department_id")
                .and(Sort.by(Sort.Direction.ASC, "number"));
        return repository.findAll(sort).stream().map(DeskResponseDTO::new).toList();
    }

    public List<DeskResponseDTO> create(Desk desk){
        if(repository.existsByDepartmentAndNumber(desk.getDepartment(), desk.getNumber())){
            throw new InvalidDeskException("Desk already exists");
        }

        repository.save(desk);
        return list();
    }
}
