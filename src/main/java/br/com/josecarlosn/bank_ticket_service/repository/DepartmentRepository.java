package br.com.josecarlosn.bank_ticket_service.repository;

import br.com.josecarlosn.bank_ticket_service.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
