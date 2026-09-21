package br.com.josecarlosn.bank_ticket_service.dto.update;

import br.com.josecarlosn.bank_ticket_service.dto.response.DepartmentResponseDTO;
import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.entity.Desk;

public record DeskUpdateDTO(
        Integer deskNumber,
        Integer departmentId
) {
    public DeskUpdateDTO(Desk desk){
        this(desk.getNumber(), desk.getDepartment().getId());
    }
}
