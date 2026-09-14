package br.com.josecarlosn.bank_ticket_service.entity;

import br.com.josecarlosn.bank_ticket_service.exceptions.TicketException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "tickets")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number", nullable = false)
    private int number;
    @Size(max = 10)
    @Column(name = "code", nullable = false)
    private String code;
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

    public void call(Long id, Desk desk){
        if ( calledAt != null || wasCanceled ){
            throw new TicketException("Cannot call a ticket that has already been called or canceled.");
        }
        if(!desk.getDepartment().getId().equals(this.department.getId())){
            throw new TicketException("Cannot call a ticket from a different department");
        }
        this.desk = desk;
        this.calledAt = LocalDateTime.now();
    }
    public void finish(){
        if (calledAt == null){
            throw new TicketException("Cannot finish a ticket that hasn't been called yet.");
        }
        if (finishedAt != null || wasCanceled){
            throw new TicketException("Cannot finish a ticket that has already been finished or canceled.");
        }
        this.finishedAt = LocalDateTime.now();
    }
    public void cancel(){
        if (wasCanceled){
            throw new TicketException("Ticket has already been canceled.");
        }
        if (calledAt != null){
            throw new TicketException("Cannot cancel a ticket that has already been called.");
        }
        this.wasCanceled = true;
    }
}
