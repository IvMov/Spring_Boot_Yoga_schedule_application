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
import java.time.Duration;
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
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private final LocalDateTime timestamp = LocalDateTime.now();

    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private EventType type = EventType.EVENT;

    @Column
    @BooleanFlag
    private Boolean isRepeatable = false; //if true -> will schedule by daysOfWeek 180 days in future? and give some groupId

    @Column
    private String groupId = "single"; // if repeatable = true -> setGroupId() some uniqueId
    //TODO: by groupId and activeDaysOfWeek - calculate and create automatically N event objects -
    // will be able to change any event (time, duration and days) in group of events or change all group.

    @Column
    private String title = "some title";

    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private EventTheme theme = EventTheme.MEDITATION; //for sort lessons by different themes

    @Column
    private String city = "Klaipeda";

    @Column
    String address = "Nesamoniu str. 11-99"; // example from admin when creating "Nesamoniu str. 11-99, second floor, room 206"

    @Column
    private String urlGoogleMaps = "https://goo.gl/maps/2aTwnb9NmkpuXrnf8"; //example -> "https://goo.gl/maps/2aTwnb9NmkpuXrnf8"

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate = LocalDate.of(2022, 05, 03); //first day of event for repeatable or day of event if not repeatable

    @Column
    private int durationDays = 1;
    //TODO: need to set activeDaysOfWeek from startDate and periodDays

    @ElementCollection
    @JoinTable(name = "events_days",
            joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private Set<DaysOfWeek> weekDays;  //in which days of week will be event if it is repeatable

    @Column
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime = LocalTime.of(18, 10); //start-time of event

    @Column
    private int durationMinutes = 60; //format 1.5 means 1hour 30 min.

    @Column(columnDefinition = "TEXT")
    private String textAbout = "Lorem ipsum"; //used to contain text of description of course
    //TODO: think how to 1) create template by which can separate p li ul?

    @Column
    private String imageSrc = "https://picsum.photos/370/370?random=1";
    //TODO: how can i store images which admin will add for new events? maybe cloud?

    @Column
    private Double commonPrice = 10.99;

    @Column
    private boolean isDiscount = false; // true -> will for all users show discountPrice

    @Column
    private Double discount = 0.00; //by default discount = 0. (from 0 to 1) percents

    @Column
    private int vacanciesLimit = 10; //how peoples can participate 1 event

    @ManyToMany
    @JoinTable(
            name = "events_users",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER)
    private Set<Payment> payments; //here all info about payment and registration

    public String getEndDate() {
        if (this.durationDays >= 2) {
            return this.startDate.plusDays(durationDays - 1).toString();
        }
        return startDate.toString();
    }

    public String getDurationHoursMinutes() {

        Duration duration = Duration.ofMinutes(getDurationMinutes());
        int hours = duration.toHoursPart();

        int minutes = duration.toMinutesPart();
        String stringMinutes = "";

        if (minutes <= 9) {
            stringMinutes = "0" + minutes;
        } else {
            stringMinutes = String.valueOf(minutes);
        }

        return hours + "h : " + stringMinutes + "m";
    }


    public int getVacanciesNow() {
        int users = this.users.size();
        return this.vacanciesLimit - users;
    }

    public String getUniqueOrRegular() {
        if (!isRepeatable) {
            return "uniqueKey";
        }
        return "regularKey";
    }

    public String getEveryOrOnly() {
        if (isRepeatable) {
            return "onlyKey";
        }
        return "everyKey";
    }

}

