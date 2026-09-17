package br.com.josecarlosn.bank_ticket_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentRequestDTO(
        @NotNull @Size(max = 100, message = "Name field can't have more than 100 characters!") @NotBlank(message = "Name field can't be empty!")
        String name,
        @NotNull @Size(max = 5, message = "Tag field can't have more than 5 characters!") @NotBlank(message = "Tag field can't be empty!")
        String tag,
        @NotNull @Size(max = 5, message = "Priority tag field can't have more than 5 characters!") @NotBlank(message = "Priority tag field can't be empty!")
        String priorityTag
) {
}
