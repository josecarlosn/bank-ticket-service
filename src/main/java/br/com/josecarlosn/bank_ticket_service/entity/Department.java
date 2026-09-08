package br.com.josecarlosn.bank_ticket_service.entity;

import br.com.josecarlosn.bank_ticket_service.dto.request.DepartmentRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "departments")
@Entity
@Getter
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


    public Department(DepartmentRequestDTO body){
        this.name = body.name();
        this.tag = body.tag();
        this.priorityTag = body.priorityTag();
    }
}
