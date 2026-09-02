package br.com.josecarlosn.bank_ticket_service.service;

import br.com.josecarlosn.bank_ticket_service.DTO.response.DeskResponseDTO;
import br.com.josecarlosn.bank_ticket_service.repository.DeskRepository;
import org.springframework.data.domain.Sort;
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
}
