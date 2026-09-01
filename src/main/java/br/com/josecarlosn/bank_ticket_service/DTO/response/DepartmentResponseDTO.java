package br.com.josecarlosn.bank_ticket_service.DTO.response;

import br.com.josecarlosn.bank_ticket_service.entity.Department;

public record DepartmentResponseDTO(Integer id, String name, String tag, String priority_tag) {
    public DepartmentResponseDTO(Department department){
        this(department.getId(), department.getName(), department.getTag(), department.getPriority_tag());
    }
}
