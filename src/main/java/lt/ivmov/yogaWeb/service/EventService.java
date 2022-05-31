package lt.ivmov.yogaWeb.service;

import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.entity.User;
import lt.ivmov.yogaWeb.enums.DaysOfWeek;
import lt.ivmov.yogaWeb.enums.EventTheme;
import lt.ivmov.yogaWeb.exception.EventNotFoundException;
import lt.ivmov.yogaWeb.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Set<User> findAllUnpaidUsers() {

        Set<User> allUnpaidUsers = new HashSet<>();

        List<Event> events = eventRepository.findAll().stream()
                .filter(event -> !event.getUsersUnpaid().isEmpty())
                .toList();

        for (Event event : events) {
            allUnpaidUsers.addAll(event.getUsersUnpaid());
        }
        return allUnpaidUsers;
    }

    public Set<User> findAllPaidUsers() {

        Set<User> allUsers = new HashSet<>();

        List<Event> events = eventRepository.findAll().stream()
                .filter(event -> !event.getUsersPaid().isEmpty())
                .toList();

        for (Event event : events) {
            allUsers.addAll(event.getUsersPaid());
        }
        return allUsers;
    }

    public Page<Event> findAllForPage(int pageSize, int pageNum) {

        Pageable pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNum);

        return eventRepository.findAll(pageable);
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

    public void createEventsByWeekDays(Event event, EventService eventService) {

        for (int i = 0; i < 90; i++) { //to schedule repeatable events for 3 month (approx. 90 days)

            for (DaysOfWeek daysOfWeek : event.getWeekDays()) {

                if (((event.getStartDate()).plusDays(i).getDayOfWeek().toString()).equals(daysOfWeek.toString())) {

                    Event inputEvent = new Event();
                    event.setGroupId(String.valueOf(((Math.random() * 999) + 1)));

                    eventService.updateEventFields(inputEvent, event);
                    inputEvent.setStartDate((event.getStartDate()).plusDays(i));
                    inputEvent.setFinalPrice(event.getFinalPriceWithDiscount());
                    inputEvent.getWeekDays().addAll(event.getWeekDays());
                    eventService.create(inputEvent);
                }
            }
        }
    }

    public Event updateEventFields(Event updatedEvent, Event sourceEvent) {

        updatedEvent.setTitle(sourceEvent.getTitle());
        updatedEvent.setType(sourceEvent.getType());
        updatedEvent.setTheme(sourceEvent.getTheme());
        updatedEvent.setStartTime(sourceEvent.getStartTime());
        updatedEvent.setStartDate(sourceEvent.getStartDate());
        updatedEvent.setCommonPrice(sourceEvent.getCommonPrice());
        updatedEvent.setDurationMinutes(sourceEvent.getDurationMinutes());
        updatedEvent.setAddress(sourceEvent.getAddress());
        updatedEvent.setDurationDays(sourceEvent.getDurationDays());
        updatedEvent.setDiscount(sourceEvent.getDiscount());
        updatedEvent.setGroupId(sourceEvent.getGroupId());
        updatedEvent.setImageSrc(sourceEvent.getImageSrc());
        updatedEvent.setUrlGoogleMaps(sourceEvent.getUrlGoogleMaps());
        updatedEvent.setTextAbout(sourceEvent.getTextAbout());
        updatedEvent.setVacanciesLimit(sourceEvent.getVacanciesLimit());
        updatedEvent.setFinalPrice(sourceEvent.getFinalPriceWithDiscount());

        return updatedEvent;
    }

    public Event update(Event event) {
        return eventRepository.save(event);
    }

    public void delete(Event event) {
        eventRepository.delete(event);
    }


}
