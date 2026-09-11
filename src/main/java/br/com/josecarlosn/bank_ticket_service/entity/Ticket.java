package br.com.josecarlosn.bank_ticket_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "tickets")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false)
    private int number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "desk_id", nullable = true)
    private Desk desk;

    @Column(name = "have_priority", nullable = false)
    private boolean havePriority;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "called_at")
    private LocalDateTime calledAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Column(name = "was_canceled", nullable = false)
    private boolean wasCanceled;

    public Ticket(Department department, boolean havePriority, int number, LocalDateTime createdAt){
        this.department = department;
        this.havePriority = havePriority;
        this.number = number;
        this.createdAt = createdAt;
    }
}
