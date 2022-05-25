package lt.ivmov.yogaWeb.controller;

import lt.ivmov.yogaWeb.entity.Activity;
import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.entity.Payment;
import lt.ivmov.yogaWeb.entity.User;
import lt.ivmov.yogaWeb.enums.ActivityStatus;
import lt.ivmov.yogaWeb.service.ActivityService;
import lt.ivmov.yogaWeb.service.EventService;
import lt.ivmov.yogaWeb.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
        event.setFinalPrice(event.getCommonPrice() * (1- event.getDiscount()));
        Event createdEvent = eventService.create(event);
        model.addAttribute("event", createdEvent);

        return "redirect:/public/schedule/event/" + createdEvent.getId();
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
