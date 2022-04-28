package lt.ivmov.yogaWeb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lt.ivmov.yogaWeb.enums.DaysOfWeek;
import lt.ivmov.yogaWeb.enums.EventTheme;
import lt.ivmov.yogaWeb.enums.EventType;
import lt.ivmov.yogaWeb.repository.EventPaymentRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "events_db")
public class Event { //All default is for non-repeatable "event" with duration ONE day

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private final LocalDateTime creationDateTime = LocalDateTime.now();

    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private EventType typeOf = EventType.EVENT; //event or lesson -> will be separated in web app

    @Column
    private boolean isRepeatable = false; //if true -> required setDurationDays()

    @Column
    private String groupId = "single"; // if repeatable = true -> setGroupId() some uniqueId
    //TODO: by groupId and activeDaysOfWeek - calculate and create automatically N event objects -
    // will be able to change any event (time, duration and days) in group of events

    @Column
    private String title;

    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private EventTheme theme; //for sort lessons by different themes

    @Column
    private String city;

    @Column
    String address; // example from admin when creating "Taikos str. 140, second floor, room 206"

    @Column
    private String urlGoogleMaps; //example copy from GM -> "https://goo.gl/maps/z7RfTVwChCQ325MJ9"

    @Column
    private LocalDate startDate; //first day of event for repeatable or day of event if not repeatable

    @Column
    private int periodDays = 1; //Example: 30days + activeDaysOfWeek {MONDAY, SUNDAY} ->
    //-> result schedule only dates which is monday sunday in nearest 30 days.

    @ElementCollection
    @JoinTable(name = "event_daysOfWeek", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "day_of_week", columnDefinition = "VARCHAR(20)", unique = true)
    private Set<DaysOfWeek> activeDaysOfWeek; //look periodDays explanation

    @Column
    private LocalTime startTime; //start-time of event

    @Column
    private double durationHours; //format 1.5 means 1hour 30 min.

    @Column(columnDefinition = "TEXT")
    private String textAbout; //used to contain text of description of course
    //TODO: ANDRIUS think how to 1) create template by which can separate p li ul? 2) or add link with image frome facebook

    @Column
    private String imageSrc; //TODO: ANDRIUS how can i store images which admin will add for new events? maybe cloud?

    @Column
    private Double commonPrice;

    @Column
    private boolean isDiscount = false; // true -> will for all students show discountPrice

    @Column
    private Double discountPrice = commonPrice; //by default prices ==. if group or some discounts -> another price.

    @Column
    private int vacanciesLimit; //how peoples can participate 1 event

    @ManyToMany
    @JoinTable( //TODO: Andrius - how to create repository for this table? to put get from db, or no need - its created inside Event/Student?
            name = "events_students",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;

    @OneToMany(mappedBy = "event")
    private Set<EventPayment> eventPayments; //here all info about payment and registration


    public String getStringTheme() {
        return this.getTheme().toString();
    }

    public String getEndDate() {
        if (this.periodDays != 1) {
            return this.startDate.plusDays(periodDays - 1).toString();
        }
        return startDate.toString();
    }

    public int getVacanciesNow() {
        Set<Integer> studentsSet = Set.of(1, 2, 3, 5); //TODO: for this moment cant take set students.
        int a = studentsSet.size();
        return this.vacanciesLimit - a;
    }

    //----!!!Commented cause - don`t need and don`t understand

    public void addStudent(Student student, boolean isPaid) {
        EventPayment eventPayment = new EventPayment();
        eventPayment.setStudent(student);
        eventPayment.setEvent(this);
        eventPayment.setPaid(isPaid);
        if (this.eventPayments == null) {
            this.eventPayments = new HashSet<>();
        }
        this.eventPayments.add(eventPayment);

        student.getEventPayments().add(eventPayment);
    }
}

