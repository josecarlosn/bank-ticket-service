package br.com.josecarlosn.bank_ticket_service.dto.response;

import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.entity.Desk;
import br.com.josecarlosn.bank_ticket_service.entity.Ticket;

import java.time.LocalDateTime;

public record TicketResponseDTO(
        Department department,
        Desk desk,
        boolean havePriority,
        int number,
        LocalDateTime createdAt,
        LocalDateTime calledAt,
        LocalDateTime finishedAt,
        boolean wasCanceled
){

    public TicketResponseDTO(Ticket ticket){
        this(
                ticket.getDepartment(),
                ticket.getDesk(),
                ticket.isHavePriority(),
                ticket.getNumber(),
                ticket.getCreatedAt(),
                ticket.getCalledAt(),
                ticket.getFinishedAt(),
                ticket.isWasCanceled()
        );
    }
}
