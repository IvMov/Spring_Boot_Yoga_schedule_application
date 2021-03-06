package lt.ivmov.yogaWeb.entity;


import lombok.Getter;
import lombok.Setter;
import lt.ivmov.yogaWeb.enums.ActivityStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

//registration of all actions which user do -> ActivityStatus.enum
@Entity
@Setter
@Getter
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private final LocalDateTime timestamp = LocalDateTime.now();

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private ActivityStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id",
            updatable = false,
            referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id",
            updatable = false,
            referencedColumnName = "id")
    private Event event;

    @OneToOne
    @JoinColumn(name = "payment_id",
            referencedColumnName = "id")
    private Payment payment;

}
