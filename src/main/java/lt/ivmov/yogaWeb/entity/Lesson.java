package lt.ivmov.yogaWeb.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "lessons_db")
public class Lesson {
    //TODO: need to divide all start and end LocalDateTime to start/endLocalDate and start/endLocalTime
    //TODO: need to add duration of lesson or end time - need to decide(
    //TODO: add groupId - if not single lesson (for single - all same groupId - Single)

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
    private boolean isRegular;



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
            name = "lessons_students",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;

}
