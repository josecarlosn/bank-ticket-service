package br.com.josecarlosn.bank_ticket_service.dto.response;

import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.entity.Desk;

public record DeskResponseDTO(int id,String departmentName, Integer number, boolean isActive) {
    public DeskResponseDTO(Desk desk){
        this(desk.getId(),desk.getDepartment().getName(), desk.getNumber(), desk.isActive());
    }
}
