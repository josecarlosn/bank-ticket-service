package br.com.josecarlosn.bank_ticket_service.DTO.response;

import br.com.josecarlosn.bank_ticket_service.entity.Department;

public record DepartmentResponseDTO(Integer id, String name) {
    public DepartmentResponseDTO(Department department){
        this(department.getId(), department.getName());
    }
}
