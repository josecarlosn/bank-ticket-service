package br.com.josecarlosn.bank_ticket_service.repository;

import br.com.josecarlosn.bank_ticket_service.entity.Department;
import br.com.josecarlosn.bank_ticket_service.entity.Desk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeskRepository extends JpaRepository<Desk, Integer> {
    boolean existsByDepartmentAndNumber(Department department, Integer number);
}
