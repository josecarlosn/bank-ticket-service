package br.com.josecarlosn.bank_ticket_service.entity;

import br.com.josecarlosn.bank_ticket_service.dto.request.DeskRequestDTO;
import br.com.josecarlosn.bank_ticket_service.exceptions.InvalidDeskException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "desks", uniqueConstraints = @UniqueConstraint(name = "uk_department_number", columnNames = {"department_id", "number"}))
@Entity
@Getter
@Setter
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
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    public Desk(Department department, Integer number){
        this.department = department;
        this.number = number;
    }
    public void deactivate(){
        if (!isActive){throw new InvalidDeskException("Desk is already inactive.");}
        this.isActive = false;
    }
    public void activate(){
        if (isActive){throw new InvalidDeskException("Desk is already active.");}
        this.isActive = true;
    }
}
