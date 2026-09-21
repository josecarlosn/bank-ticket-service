package br.com.josecarlosn.bank_ticket_service.entity;

import br.com.josecarlosn.bank_ticket_service.dto.request.DepartmentRequestDTO;
import br.com.josecarlosn.bank_ticket_service.exceptions.InvalidDepartmentException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Table(name = "departments")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 5)
    @Column(name = "tag", unique = true, nullable = false)
    private String tag;

    @Size(max = 5)
    @Column(name = "priority_tag", unique = true, nullable = false)
    private String priorityTag;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    public Department(String name, String tag, String priorityTag){
        this.name = name;
        this.tag = tag;
        this.priorityTag = priorityTag;
    }
    public void activate(){
        if (isActive){throw new InvalidDepartmentException("Department is already active.");}
        this.isActive = true;
    }
    public void deactivate(){
        if (!isActive){throw new InvalidDepartmentException("Department is already inactive.");}
        this.isActive = false;
    }
}
