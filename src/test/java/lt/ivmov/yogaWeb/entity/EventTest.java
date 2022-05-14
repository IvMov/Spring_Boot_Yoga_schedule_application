package lt.ivmov.yogaWeb.entity;

import lt.ivmov.yogaWeb.enums.DaysOfWeek;
import lt.ivmov.yogaWeb.enums.EventTheme;
import lt.ivmov.yogaWeb.enums.EventType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class EventTest {

    @Test
    void isGetEndDateCalculateCorrect() {
        Event event = createMockEvent();

        //if periodDays null
        assertThat(event.getEndDate()).isEqualTo("2022-05-12");
        event.setPeriodDays(1); //if 1 day
        assertThat(event.getEndDate()).isEqualTo(event.getStartDate().toString());
        event.setPeriodDays(2); //if 2 or more days
        assertThat(event.getEndDate()).isEqualTo(event.getStartDate().plusDays(1).toString());

    }

    @Test
    void isGetStartTimeMinShowCorrect() {
        Event event = createMockEvent();

        assertThat(event.getStartTimeMin()).isEqualTo("22:05");
        event.setStartTime(LocalTime.of(00,00));
        assertThat(event.getStartTimeMin()).isEqualTo("00:00");

    }

    @Test
    void isGetDurationHourMinuteCorrectFormated() { //will be refactored with main field -> duration
        Event event = createMockEvent();

        assertThat(event.getDurationHourMinute()).isEqualTo("no info");
        event.setDurationHours(1.00);
        assertThat(event.getDurationHourMinute()).isEqualTo("1h : 00m");
        event.setDurationHours(0.99);
        assertThat(event.getDurationHourMinute()).isEqualTo("0h : 59m");
        event.setDurationHours(2.5);
        assertThat(event.getDurationHourMinute()).isEqualTo("2h : 30m");

    }

    private Event createMockEvent() {
        Event event = new Event();
        event.setStartDate(LocalDate.of(2022, 05, 12));
        event.setStartTime(LocalTime.of(22, 05));
        return event;
    }
}
