package br.com.josecarlosn.bank_ticket_service.repository;

import br.com.josecarlosn.bank_ticket_service.entity.Department;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    boolean existsById(@NonNull Integer departmentId);
    boolean existsByName(String name);
    boolean existsByTag(String tag);
    boolean existsByPriorityTag(String priorityTag);
    List<Department> findByIsActiveTrue(Sort sort);

}
