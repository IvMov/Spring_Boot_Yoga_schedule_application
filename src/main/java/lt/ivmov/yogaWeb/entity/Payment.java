package lt.ivmov.yogaWeb.entity;

import lombok.Setter;
import lt.ivmov.yogaWeb.enums.PaymentMethod;
import lt.ivmov.yogaWeb.enums.PaymentType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Setter
@Table(name = "payments")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private final LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "`type`")
    @Enumerated(EnumType.STRING)
    private PaymentType type = PaymentType.INCOME;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentMethod method = PaymentMethod.BANK;

    @Column
    private Boolean isPaid = false;

    @Column
    private Double credits = 0.00; //always 0 for income
    //TODO: 2022-05-05 if INCOME - user.credits = summ; if cost summ = user.credits
    //TODO: get credits if user have it(admin will see how need to total payment and after get payment - can press that paid)

    @Column
    private Double sum; //credits + paid by user.

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", updatable = false, referencedColumnName = "id")
    private Event event; //null for income - only way?

}
