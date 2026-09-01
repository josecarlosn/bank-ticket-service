package br.com.josecarlosn.bank_ticket_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "desks", uniqueConstraints = @UniqueConstraint(name = "uk_department_number", columnNames = {"department_id", "number"}))
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Desk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @NotNull
    private Integer number;
}
