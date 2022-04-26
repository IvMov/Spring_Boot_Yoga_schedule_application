package lt.ivmov.yogaWeb.entity;


import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Table(name = "students")
public class Student {
    //TODO: refactor all to customers?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private final LocalDateTime registrationDateTime = LocalDateTime.now();

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
    private String email;

    @Column
    private String about;

    @ManyToMany(mappedBy = "students") //TODO: also need to refactor - look in Events -> Set<Student>
    private Set<Event> events_set;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "student")
    private Set<Subscription> subscriptions;

}
