package br.com.josecarlosn.bank_ticket_service.dto.update;

import br.com.josecarlosn.bank_ticket_service.entity.Department;

public record DepartmentUpdateDTO(
        String name,
        String tag,
        String priorityTag
) {
    public DepartmentUpdateDTO(Department department){
        this(department.getName(), department.getTag(), department.getPriorityTag());
    }
}
