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
import java.util.HashSet;
import java.util.Set;

//event(lessons/event) which will be grouped and used for users registration on it.
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

    //if repeatable == true -> will schedule by daysOfWeek 90 days in future
    @Column
    @BooleanFlag
    private Boolean isRepeatable = false;

    // if repeatable == true -> setGroupId() some uniqueId
    @Column
    private String groupId = "single";

    @Column
    private String title = "some title";

    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private EventTheme theme = EventTheme.MEDITATION;

    @Column
    private String city = "Klaipeda";

    @Column
    String address = "Nesamoniu str. 11-99";

    @Column
    private String urlGoogleMaps = "https://goo.gl/maps/2aTwnb9NmkpuXrnf8";

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate = LocalDate.of(2025, 10, 04);

    @Column
    private int durationDays = 1;

    //in which days of week will be event if it is repeatable or more than 1 day
    @ElementCollection
    @JoinTable(name = "events_days",
            joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private Set<DaysOfWeek> weekDays = new HashSet<>();

    @Column
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime = LocalTime.of(18, 10);

    @Column
    private int durationMinutes = 60;

    //TODO: think how to 1) create template by which can separate p li ul?
    @Column(columnDefinition = "TEXT")
    private String textAbout = "Lorem ipsum";

    //link to cloud img (temporary used random img)
    @Column
    private String imageSrc = "https://picsum.photos/370/370?random=1";

    @Column
    private Double commonPrice = 10.99;

    //by default discount = 0. (from 0 to 1) percents
    @Column
    private Double discount = 0.00;

    @Column
    private Double finalPrice = 0.00;

    @Column
    private int vacanciesLimit = 10;

    @ManyToMany
    @JoinTable(
            name = "paid_events_users",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> usersPaid = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "unpaid_events_users",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> usersUnpaid = new HashSet<>();

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Activity> activities = new HashSet<>();

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Payment> payments = new HashSet<>();


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

    public double getFinalPriceWithDiscount() {
        return getCommonPrice() * (1 - getDiscount());
    }

    //TODO: implement it in registration/payment/confirmation - to limit users
    public int getVacanciesNow() {
        int users = this.usersPaid.size();
        return this.vacanciesLimit - (usersPaid.size() + usersUnpaid.size());
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

