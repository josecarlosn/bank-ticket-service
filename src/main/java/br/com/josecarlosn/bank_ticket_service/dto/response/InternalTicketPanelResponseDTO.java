package br.com.josecarlosn.bank_ticket_service.dto.response;

import br.com.josecarlosn.bank_ticket_service.entity.Ticket;

import java.time.LocalDateTime;

public record InternalTicketPanelResponseDTO(Long id, String code, String departmentName, Integer deskNumber, LocalDateTime createdAt, LocalDateTime finishedAt, boolean wasCanceled) {
    public InternalTicketPanelResponseDTO(Ticket ticket){
        this(
                ticket.getId(),
                ticket.getCode(),
                ticket.getDepartment().getName(),
                ticket.getDesk() != null ? ticket.getDesk().getNumber() : null,
                ticket.getCreatedAt(),
                ticket.getFinishedAt(),
                ticket.isWasCanceled());
    }
}
