package lt.ivmov.yogaWeb.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

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
        event.setPeriodDays(days);
        if (days >= 2) {
            assertThat(event.getEndDate()).isEqualTo(event.getStartDate().plusDays(days - 1).toString());
        } else {
            assertThat(event.getEndDate()).isEqualTo(event.getStartDate().toString());
        }


    }

    @Test
    void isGetStartTimeMinShowCorrect() {
        Event event = createTestEvent();

        assertThat(event.getStartTimeMin()).isEqualTo("22:05");
        event.setStartTime(LocalTime.of(00, 00));
        assertThat(event.getStartTimeMin()).isEqualTo("00:00");

    }

    @ParameterizedTest
    @ValueSource(doubles = {0.01, 0.50, 0.99, 1.00, 24.00})
    void isGetDurationHourMinuteCorrectFormated(double duration) { //will be refactored with main field -> duration
        Event event = createTestEvent();

        assertThat(event.getDurationHourMinute()).isEqualTo("no info");

        event.setDurationHours(duration);
        assertThat(event.getDurationHourMinute()).isEqualTo(getResultTimeNotNullable(duration));

    }

    private String getResultTimeNotNullable(double duration) {

        double fractionalPart = duration % 1;
        double integralPart = duration - fractionalPart;
        String minutes = "";

        if (fractionalPart != 0) {
            minutes = String.format("%.0f", fractionalPart * 60);
        } else {
            minutes = "00";
        }

        return ((int) integralPart + "h : " + minutes + "m");
    }

    private Event createTestEvent() {
        Event event = new Event();
        event.setStartDate(LocalDate.of(2022, 05, 12));
        event.setStartTime(LocalTime.of(22, 05));
        return event;
    }
}
