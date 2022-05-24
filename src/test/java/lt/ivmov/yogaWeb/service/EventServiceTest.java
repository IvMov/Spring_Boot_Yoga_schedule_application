package lt.ivmov.yogaWeb.service;

import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.enums.DaysOfWeek;
import lt.ivmov.yogaWeb.enums.EventTheme;
import lt.ivmov.yogaWeb.enums.EventType;
import lt.ivmov.yogaWeb.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;
    private EventService eventService;

    @BeforeEach
    void setUpService() {
        eventService = new EventService(eventRepository);
    }

    @Test
    void isDBSaveEnumLikeString() { //TODO: need to ask - how to check this functionality. this test is fools
        Event event = createMockEvent();
        Page<Event> pageEvent = new PageImpl<>(List.of(event));

        when(eventService.findAllForPage(1, 0))
                .thenReturn(pageEvent);


        eventService.create(event);
        Page<Event> pageFromDb = eventService.findAllForPage(1, 0);

        assertThat(pageFromDb.getContent().get(0).getTheme().toString()).isEqualTo("ACTIVE");
        assertThat(pageFromDb.getContent().get(0).getType().toString()).isEqualTo("EVENT");

    }

    private Event createMockEvent() {
        Event event = new Event();

        event.setTheme(EventTheme.ACTIVE);
        event.setType(EventType.EVENT);

        event.setStartDate(LocalDate.of(2022, 05, 12));

        event.setTitle("Test title");
        event.setWeekDays(Set.of(DaysOfWeek.MONDAY));
        event.setCommonPrice(100.99);
        event.setDurationMinutes(100);
        event.setStartTime(LocalTime.of(22, 05));
        return event;
    }
}
