package lt.ivmov.yogaWeb.entity;

import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Setter
@Table(name = "events_payments")
@IdClass(EventPayment.class)
public class EventPayment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_of_action")
    private final LocalDateTime timeOfCreation = LocalDateTime.now();

    @Column(name = "is_paid")
    private boolean isPaid;

    @Column
    private double creditsPaid = 0.00;
    //TODO: get credits if user have it(admin will see how need to total payment and after get payment - can press that paid)

    @Column
    private double totalPaid = 0.00; //if isPaid -> take price from event price to autofill form.

    @ManyToOne
    @JoinColumn(name = "student_id", updatable = false, referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "event_id", updatable = false, referencedColumnName = "id")
    private Event event;

}
