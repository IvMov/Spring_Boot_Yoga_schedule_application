package lt.ivmov.yogaWeb.controller;

import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.service.EventService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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

        event.setFinalPrice(event.getFinalPriceWithDiscount());

        Event createdEvent = eventService.create(event);
        model.addAttribute("event", createdEvent);

        return "redirect:/public/schedule/event/" + createdEvent.getId();
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
