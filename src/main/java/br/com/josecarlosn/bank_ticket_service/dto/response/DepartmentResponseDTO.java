package br.com.josecarlosn.bank_ticket_service.dto.response;

import br.com.josecarlosn.bank_ticket_service.entity.Department;

public record DepartmentResponseDTO(Integer id, String name, String tag, String priorityTag) {
    public DepartmentResponseDTO(Department department){
        this(department.getId(), department.getName(), department.getTag(), department.getPriorityTag());
    }
}
