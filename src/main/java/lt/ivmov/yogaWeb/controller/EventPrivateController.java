package lt.ivmov.yogaWeb.controller;

import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.enums.DaysOfWeek;
import lt.ivmov.yogaWeb.service.EventService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/private")
public class EventPrivateController {

    private final EventService eventService;

    public EventPrivateController(EventService eventService) {
        this.eventService = eventService;
    }


    @GetMapping("/schedule/event/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "new-event";
    }

    @PostMapping("/schedule/event/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createEvent(Event event, Model model) {

        if (event.getIsRepeatable()) {

            LocalDate start = event.getStartDate();
            event.setGroupId(String.valueOf(((Math.random() * 999) + 1)));

            eventService.create(event);

            for (int i = 1; i < 90; i++) { //schedule repeatable events for 3 month (approx. 90 days)

                for (DaysOfWeek daysOfWeek : event.getWeekDays()) {

                    if ((start.plusDays(i).getDayOfWeek().toString()).equals(daysOfWeek.toString())) {
                        Event inputEvent = new Event();
                        eventService.updateEventFields(inputEvent, event);
                        inputEvent.setStartDate(start.plusDays(i));
                        inputEvent.getWeekDays().addAll(event.getWeekDays());
                        eventService.create(inputEvent);
                    }
                }
            }

            return "redirect:/public/schedule";
        } else {

            event.setFinalPrice(event.getFinalPriceWithDiscount());
            eventService.create(event);

            return "redirect:/public/schedule";
        }


    }

    @GetMapping("/schedule/event/{id}/change")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEventUpdateForm(@PathVariable(name = "id") Long id,
                                     Model model) {

        Event event = eventService.findById(id);

        model.addAttribute("event", event);

        return "update-event";
    }

    @PostMapping("/schedule/event/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateEvent(@PathVariable(name = "id") Long id,
                              Event event,
                              Model model) {

        Event changedEvent = eventService.findById(id);

        changedEvent = eventService.updateEventFields(changedEvent, event);

        Event updatedEvent = eventService.update(changedEvent);
        model.addAttribute("event", updatedEvent);

        return "redirect:/public/schedule";
    }

    @PostMapping("/schedule/event/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEvent(@PathVariable(name = "id") Long id) {

        eventService.delete(eventService.findById(id));

        return "redirect:/public/schedule";
    }


    @GetMapping("/schedule/event/{id}")
    public String getEventPage(
            @PathVariable(name = "id") Long id,
            Model model) {
        Event foundEvent = eventService.findById(id);
        model.addAttribute("event", foundEvent);
        return "event";
    }

}
