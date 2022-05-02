package lt.ivmov.yogaWeb.entity;

import jdk.jfr.BooleanFlag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lt.ivmov.yogaWeb.enums.DaysOfWeek;
import lt.ivmov.yogaWeb.enums.EventTheme;
import lt.ivmov.yogaWeb.enums.EventType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "`events`")
public class Event { //All default is for non-repeatable "event" with duration ONE day

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private final LocalDateTime timeStamp = LocalDateTime.now();

    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private EventType typeOf = EventType.EVENT; //event or lesson -> will be separated in web app

    @Column
    @BooleanFlag
    private Boolean isRepeatable = false; //if true -> required setDurationDays()

    @Column
    private String groupId = "single"; // if repeatable = true -> setGroupId() some uniqueId
    //TODO: by groupId and activeDaysOfWeek - calculate and create automatically N event objects -
    // will be able to change any event (time, duration and days) in group of events

    @Column
    private String title = "New title";

    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private EventTheme theme = EventTheme.MEDITATION; //for sort lessons by different themes

    @Column
    private String city = "Klaipeda";

    @Column
    String address = "Nesamoniu str. 11-99"; // example from admin when creating "Taikos str. 140, second floor, room 206"

    @Column
    private String urlGoogleMaps = "https://goo.gl/maps/2aTwnb9NmkpuXrnf8"; //example copy from GM -> "https://goo.gl/maps/z7RfTVwChCQ325MJ9"

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate = LocalDate.of(2022, 05, 03); //first day of event for repeatable or day of event if not repeatable

    @Column
    private int periodDays = 1; //Example: 30days + activeDaysOfWeek {MONDAY, SUNDAY} ->
    //-> result schedule only dates which is monday sunday in nearest 30 days.

    @ElementCollection
    @JoinTable(name = "event_daysOfWeek", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private Set<DaysOfWeek> activeDaysOfWeek = Set.of(DaysOfWeek.Md,
            DaysOfWeek.Tu,
            DaysOfWeek.Wd,
            DaysOfWeek.Th,
            DaysOfWeek.Fr,
            DaysOfWeek.St,
            DaysOfWeek.Su); //look periodDays explanation

    @Column
    private LocalTime startTime = LocalTime.of(18, 05); //start-time of event

    @Column
    private Double durationHours = 1.5; //format 1.5 means 1hour 30 min.

    @Column(columnDefinition = "TEXT")
    private String textAbout = "Lorem ipsum"; //used to contain text of description of course
    //TODO: ANDRIUS think how to 1) create template by which can separate p li ul? 2) or add link with image frome facebook

    @Column
    private String imageSrc = "https://picsum.photos/370/370?random=1"; //TODO: ANDRIUS how can i store images which admin will add for new events? maybe cloud?

    @Column
    private Double commonPrice = 10.99;

    @Column
    private boolean isDiscount = false; // true -> will for all students show discountPrice

    @Column
    private Double discountPrice = commonPrice; //by default prices ==. if group or some discounts -> another price.

    @Column
    private int vacanciesLimit = 10; //how peoples can participate 1 event

    @ManyToMany
    @JoinTable( //TODO: Andrius - how to create repository for this table? to put get from db, or no need - its created inside Event/Student?
            name = "events_students",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private Set<EventPayment> eventPayments; //here all info about payment and registration


    public String getStringTheme() {
        return this.getTheme().toString();
    }
    public String getStringType() {
        return this.getTypeOf().toString();
    }

    public String getEndDate() {
        if (this.periodDays != 1) {
            return this.startDate.plusDays(periodDays - 1).toString();
        }
        return startDate.toString();
    }

    public String getStartTimeMin() {
        return startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getDurationHourMinute() {

        double fractionalPart = getDurationHours() % 1;
        double integralPart = getDurationHours() - fractionalPart;
        String minutes = String.format("%.0f", fractionalPart * 60);

        return ( (int) integralPart + "h : " + minutes + "m");
    }


    public int getVacanciesNow() {
        Set<Integer> studentsSet = Set.of(1, 2, 3, 5); //TODO: for this moment cant take set students.
        int b = this.eventPayments.size();
//        int a = studentsSet.size();
        return this.vacanciesLimit - b;
    }

    public String getUniqueOrRegular() {
        if("single".equals(getGroupId())){
            return "unique";
        }
        return "regular";
    }

    public String getEveryOrOnly(){
        if(isRepeatable){
            return "Only";
        }
        return "Every";
    }


//    public String getDays(){
//        for (Iterator<DaysOfWeek> it = activeDaysOfWeek.iterator(); it.hasNext(); ) {
//        DaysOfWeek day = it.next();
//
//
//    }

    //----!!!Commented cause - don`t need and don`t understand

//    public void addStudent(Student student, boolean isPaid) {
//        EventPayment eventPayment = new EventPayment();
//        eventPayment.setStudent(student);
//        eventPayment.setEvent(this);
//        eventPayment.setPaid(isPaid);
//        if (this.eventPayments == null) {
//            this.eventPayments = new HashSet<>();
//        }
//        this.eventPayments.add(eventPayment);
//
//        student.getEventPayments().add(eventPayment);
//    }
}

