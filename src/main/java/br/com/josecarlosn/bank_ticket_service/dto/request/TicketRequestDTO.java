package br.com.josecarlosn.bank_ticket_service.dto.request;

import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.entity.Desk;

public record TicketRequestDTO(
        Department department,
        boolean havePriority
) {
}
