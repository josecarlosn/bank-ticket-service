package br.com.josecarlosn.bank_ticket_service.repository;

import br.com.josecarlosn.bank_ticket_service.entity.Department;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    boolean existsById(@NonNull Integer departmentId);
    boolean existsByName(String name);
    boolean existsByTag(String tag);
    boolean existsByPriorityTag(String priorityTag);

}
