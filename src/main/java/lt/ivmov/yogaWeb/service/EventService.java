package lt.ivmov.yogaWeb.service;

import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.enums.EventTheme;
import lt.ivmov.yogaWeb.exception.EventNotFoundException;
import lt.ivmov.yogaWeb.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Page<Event> findAllForPage(int pageSize, int pageNum) {

        Pageable pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNum);

        return eventRepository.findAll(pageable);
    }

    public List<Event> findAllUnpaid() {

        return eventRepository.findAll().stream()
                .filter(event -> event.getStartDate().isAfter(LocalDate.now()))
                .filter(event -> !event.getUsersUnpaid().isEmpty())
                .toList();
    }

    public List<Event> findAllPaid() {

        return eventRepository.findAll().stream()
                .filter(event -> event.getStartDate().isAfter(LocalDate.now()))
                .filter(event -> !event.getUsersPaid().isEmpty())
                .toList();
    }

    public Page<Event> findAllByTheme(EventTheme themeName, int pageSize, int pageNum) {

        Pageable pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNum);
        return eventRepository.findAllByTheme(themeName, pageable);
    }

    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(EventNotFoundException::new);
    }

    public Event create(Event event) {
        return eventRepository.save(event);
    }

    public Event update(Event event) {
        return eventRepository.save(event);
    }


}
