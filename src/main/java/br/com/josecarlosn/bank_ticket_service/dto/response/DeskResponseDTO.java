package br.com.josecarlosn.bank_ticket_service.dto.response;

import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.entity.Desk;

public record DeskResponseDTO(int id,DepartmentResponseDTO department, Integer number) {
    public DeskResponseDTO(Desk desk){
        this(desk.getId(),new DepartmentResponseDTO(desk.getDepartment()), desk.getNumber());
    }
}
