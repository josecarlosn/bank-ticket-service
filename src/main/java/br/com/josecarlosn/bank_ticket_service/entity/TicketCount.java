package br.com.josecarlosn.bank_ticket_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ticket_counts", uniqueConstraints = @UniqueConstraint(name = "uk_ticket_count", columnNames = {"department_id", "have_priority", "date"}))
public class TicketCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "department_id", nullable = false)
    private Integer departmentId;
    @Column(name = "have_priority", nullable = false)
    private boolean havePriority;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "last_number", nullable = false)
    private Integer lastNumber = 1;

    public TicketCount(Integer departmentId, boolean havePriority, LocalDate date){
        this.departmentId = departmentId;
        this.havePriority = havePriority;
        this.date = date;
    }
}
