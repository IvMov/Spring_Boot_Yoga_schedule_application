package lt.ivmov.yogaWeb.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "events_db")

public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String place;

    @Column
    private final LocalDateTime creationDateTime = LocalDateTime.now();

    @Column
    private LocalDateTime startDateTime;

    @Column
    private LocalDateTime endDateTime;

    @Column
    private String about;

    @Column
    private String imageSrc;

    @Column
    private Double price;

    @Column
    private int vacanciesLimit;

    @ManyToMany
    @JoinTable(
            name = "events_students",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;


}
