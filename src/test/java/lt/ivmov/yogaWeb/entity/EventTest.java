package lt.ivmov.yogaWeb.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class EventTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1, 10, Integer.MAX_VALUE})
    void isGetEndDateCalculateCorrect(int days) {
        Event event = createTestEvent();

        //test for days = null
        assertThat(event.getEndDate()).isEqualTo("2022-05-12");
        event.setDurationDays(days);
        if (days >= 2) {
            assertThat(event.getEndDate()).isEqualTo(event.getStartDate().plusDays(days - 1).toString());
        } else {
            assertThat(event.getEndDate()).isEqualTo(event.getStartDate().toString());
        }


    }

    @ParameterizedTest
    @ValueSource(ints = {1, 7, 10, 23, 30, 59, 61, 150, 363})
    void isGetDurationHourMinuteCorrectFormatted(int duration) { //will be refactored with main field -> duration
        Event event = createTestEvent();

        event.setDurationMinutes(duration);
        assertThat(event.getDurationHoursMinutes()).isEqualTo(getResultTimeNotNullable(duration));

    }

    private String getResultTimeNotNullable(int duration) {

        Duration durationInMinutes = Duration.ofMinutes(duration);

        int hours = durationInMinutes.toHoursPart();
        int minutes = durationInMinutes.toMinutesPart();
        String stringMinutes = "";

        if (minutes <= 9) {
            stringMinutes = "0" + minutes;
        } else {
            stringMinutes = String.valueOf(minutes);
        }

        return hours + "h : " + stringMinutes + "m";
    }

    private Event createTestEvent() {
        Event event = new Event();
        event.setStartDate(LocalDate.of(2022, 05, 12));
        return event;
    }
}
