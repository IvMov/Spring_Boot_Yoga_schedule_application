package lt.ivmov.yogaWeb.entity;

import lombok.Getter;
import lombok.Setter;
import lt.ivmov.yogaWeb.enums.PaymentMethod;
import lt.ivmov.yogaWeb.enums.PaymentType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@Table(name = "payments")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private final LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "`type`")
    @Enumerated(EnumType.STRING)
    private PaymentType type = PaymentType.COST;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentMethod method = PaymentMethod.BANK;

    @Column
    private Double sum = 0.00; //credits + paid by user.

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

    public String getPaymentDate() {
        String year = String.valueOf(getTimestamp().getYear());
        String month = String.valueOf(getTimestamp().getMonth());
        String day = String.valueOf(getTimestamp().getDayOfMonth());

        String hours = String.valueOf(getTimestamp().getHour());
        String minutes = String.valueOf(getTimestamp().getMinute());

        return year + "-" + month + "-" + day + " / " + hours + ":" + minutes;
    }

}
