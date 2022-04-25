package lt.ivmov.yogaWeb.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime payDateTime;


    @Column
    private Double paidSum;

    @Column
    private int countCredits; //credits meaning - count of paid events or lessons free to use

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    public Student student;
}
