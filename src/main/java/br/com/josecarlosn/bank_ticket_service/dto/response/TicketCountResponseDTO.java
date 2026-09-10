package br.com.josecarlosn.bank_ticket_service.dto.response;

import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.entity.TicketCount;


import java.time.LocalDate;

public record TicketCountResponseDTO(Integer departmentId, boolean havePriority, LocalDate date, Integer lastNumber) {
    public TicketCountResponseDTO(TicketCount ticketCount){
        this(ticketCount.getDepartmentId(), ticketCount.isHavePriority(), ticketCount.getDate(), ticketCount.getLastNumber());
    }
}
