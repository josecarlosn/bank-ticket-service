package br.com.josecarlosn.bank_ticket_service.dto.request;

import br.com.josecarlosn.bank_ticket_service.entity.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeskRequestDTO(
        @NotNull
        Department department,
        @NotNull
        Integer number
) {}
