package br.com.josecarlosn.bank_ticket_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DepartmentRequestDTO(
        @NotNull @NotBlank(message = "Name field can't be empty!")
        String name,
        @NotNull @NotBlank(message = "Tag field can't be empty!")
        String tag,
        @NotNull @NotBlank(message = "Priority tag field can't be empty!")
        String priorityTag
) {
}
