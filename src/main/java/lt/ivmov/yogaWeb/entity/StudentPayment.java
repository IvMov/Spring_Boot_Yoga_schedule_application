package lt.ivmov.yogaWeb.entity;

import lt.ivmov.yogaWeb.enums.PaymentType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_payments")
public class StudentPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private final LocalDateTime dateTime = LocalDateTime.now();

    @Column
    private PaymentType paymentType;

    @Column
    private double creditsInput; // how much credits will + to student.credits

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    public Student student;
}
