package br.com.josecarlosn.bank_ticket_service.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TicketCountRequestDTO(
        @NotNull
        Integer departmentId,
        @NotNull
        boolean havePriority,
        @NotNull
        LocalDate date
) {
}
