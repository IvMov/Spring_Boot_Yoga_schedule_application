package lt.ivmov.yogaWeb.entity;


import lombok.Getter;
import lombok.Setter;
import lt.ivmov.yogaWeb.enums.DaysOfWeek;
import lt.ivmov.yogaWeb.enums.UserRoles;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    //TODO: refactor all to customers want User - but mySql is blocked user?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private final LocalDateTime timeStamp = LocalDateTime.now();

    @Column
    private String userName;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String city;

    @Column
    private String phone;

    @Column
    private String email; //used for log in

    @Column
    private String password; //used for log in

    @Column
    private String about;

    @Column
    private Double creditsBalance; // 1 credit = 1 euro
    //TODO: if subscriber cancel 1 event of group he can or book in same group 1 lesson automatically or get 75% of price of 1 event
    //TODO: or if cancel - no credits are return when cancel from group
    //TODO: if single event - canceled - and was paid - return here 1euro = 1credits, which can be used for book any event by user if price is enough (in one + will apear reservation and payment +) or
    // admin will see that summ to pay lower and can + when will get summ

    @ManyToMany(mappedBy = "users")
    private Set<Event> events;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user")
    private Set<Payment> payments;

    @ElementCollection
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<UserRoles> roles;

}
