package br.com.josecarlosn.bank_ticket_service.dto.request;

import br.com.josecarlosn.bank_ticket_service.entity.Ticket;

public record TicketActionRequestDTO(
        int deskId
) {
    public TicketActionRequestDTO(Ticket ticket){

        this(
                ticket.getDesk().getId());
    }
}
