package br.com.josecarlosn.bank_ticket_service.repository;

import br.com.josecarlosn.bank_ticket_service.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
