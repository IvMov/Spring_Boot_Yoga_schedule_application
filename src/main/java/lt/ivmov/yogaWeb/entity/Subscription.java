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
    @JoinColumn(name = "user_id", columnDefinition = "users", referencedColumnName = "id")
    private Long userId;

    @Column
    private Double paidSum;

    @Column
    private int countCredits; //credits meaning - count of paid events or lessons free to use

}
