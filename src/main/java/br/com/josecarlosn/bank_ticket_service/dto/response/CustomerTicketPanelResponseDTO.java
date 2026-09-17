package br.com.josecarlosn.bank_ticket_service.dto.response;

import br.com.josecarlosn.bank_ticket_service.entity.Ticket;

import java.time.LocalDateTime;

public record CustomerTicketPanelResponseDTO(String code, String departmentName, Integer deskNumber, LocalDateTime createdAt, LocalDateTime calledAt) {
    public CustomerTicketPanelResponseDTO(Ticket ticket){
        this(
            ticket.getCode(),
            ticket.getDepartment().getName(),
            ticket.getDesk().getNumber(),
             ticket.getCreatedAt(),
             ticket.getCalledAt()
        );
    }
}
