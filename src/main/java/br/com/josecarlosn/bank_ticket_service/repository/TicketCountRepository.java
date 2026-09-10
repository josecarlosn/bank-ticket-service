package br.com.josecarlosn.bank_ticket_service.repository;

import br.com.josecarlosn.bank_ticket_service.entity.TicketCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TicketCountRepository extends JpaRepository<TicketCount, Integer> {
    boolean existsByDepartmentIdAndHavePriorityAndDate(Integer departmentId, boolean havePriority, LocalDate date);
}

