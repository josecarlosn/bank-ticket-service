package br.com.josecarlosn.bank_ticket_service.dto.update;

import br.com.josecarlosn.bank_ticket_service.entity.Department;
import jakarta.validation.constraints.Size;

public record DepartmentUpdateDTO(
        @Size(max = 100, min = 1)
        String name,
        @Size(max = 5, min = 1)
        String tag,
        @Size(max = 5, min = 1)
        String priorityTag
) {
    public DepartmentUpdateDTO(Department department){
        this(department.getName(), department.getTag(), department.getPriorityTag());
    }
}
