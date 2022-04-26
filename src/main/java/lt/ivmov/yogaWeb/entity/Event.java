package lt.ivmov.yogaWeb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lt.ivmov.yogaWeb.enums.DaysOfWeek;
import lt.ivmov.yogaWeb.enums.EventTheme;
import lt.ivmov.yogaWeb.enums.EventType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "events_db")
public class Event { //All default is for nonrepeteble "event" duration one day

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private final LocalDateTime creationDateTime = LocalDateTime.now();

    @Column
    private EventType type_of = EventType.EVENT;

    @Column
    private boolean repeatable = false; //if true -> required setDurationDays()

    @Column
    private String groupId = "Single"; // if repeatable -> setGroupId() unique

    @Column
    private String title;

    @Column
    private EventTheme eventTheme;

    @Column
    private String city;

    @Column
    String abstractAddress; // example from admin when creating "Taikos str. 140, second floor, room 206"

    @Column
    private String urlGoogleMaps; //example copy from GM -> "https://goo.gl/maps/z7RfTVwChCQ325MJ9"

    @Column
    private LocalDate startDate; //first day of event

    @Column
    private int periodDays = 1; //Example: 30 - means 30 days active event, but not every day

    @ElementCollection
    @JoinTable(name = "event_daysOfWeek", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "day_of_week")
    private Set<DaysOfWeek> activeDaysOfWeek; //schedule will check and match only this days.
    //TODO: think how to change schedule for now, or for long term.

    @Column
    private LocalTime startTime; //start-time for every day if not one day

    @Column
    private int durationHours; //TODO: think how to set / change duration for different days

    @Column
    private String about;

    @Column
    private String imageSrc; //TODO: how can i store images which admin will add for new events? maybe cloud?

    @Column
    private Double price;

    @Column
    private int vacanciesLimit;

    @ManyToMany
    //TODO: refactor to 1) create new entity with boolean payed,or 2) leave this table and add new with entity
    @JoinTable(
            name = "events_students",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;


}
