package br.com.josecarlosn.bank_ticket_service.dto.response;

import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.entity.Desk;
import br.com.josecarlosn.bank_ticket_service.entity.Ticket;

import java.time.LocalDateTime;

public record TicketResponseDTO(
        String code,
        String departmentName,
        boolean havePriority,
        Integer deskNumber,
        LocalDateTime createdAt,
        LocalDateTime calledAt,
        LocalDateTime finishedAt,
        boolean wasCanceled
){

    public TicketResponseDTO(Ticket ticket){
        this(
                ticket.getCode(),
                ticket.getDepartment().getName(),
                ticket.isHavePriority(),
                ticket.getDesk() != null ? ticket.getDesk().getNumber() : null ,
                ticket.getCreatedAt(),
                ticket.getCalledAt(),
                ticket.getFinishedAt(),
                ticket.isWasCanceled()
        );
    }
}
