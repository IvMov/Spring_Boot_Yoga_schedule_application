package lt.ivmov.yogaWeb.controller;

import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.entity.Payment;
import lt.ivmov.yogaWeb.entity.User;
import lt.ivmov.yogaWeb.service.EventService;
import lt.ivmov.yogaWeb.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/private/schedule")
public class EventPrivateController {

    private final EventService eventService;
    private final UserService userService;

    public EventPrivateController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/new-event")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "new-event";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createEvent(Event event, Model model) {
        Event createdEvent = eventService.create(event);
        model.addAttribute("event", createdEvent);

        return "redirect:/public/schedule";
    }

    @GetMapping("/event-{id}")
    public String getEventPage(
            @PathVariable(name = "id") Long id,
            Model model) {
        Event foundEvent = eventService.findById(id);
        model.addAttribute("event", foundEvent);
        return "event";
    }

    @PostMapping("/event-{evId}/add-user")
    @PreAuthorize("hasRole('USER')")
    public String addUserToEvent(@PathVariable(name = "evId") Long id,
                                 Authentication authentication,
                                 Model model) {

        String email = ((User) authentication.getPrincipal()).getEmail();
        User user = userService.findByEmail(email);
        Event event = eventService.findById(id);

        Payment payment = new Payment();

        event.getUsers().add(user);
        event.getPayments().add(payment);

        user.getEvents().add(event);
        user.getPayments().add(payment);

        payment.setEvent(event);
        payment.setUser(user);


        eventService.update(event);
        userService.update(user);

        return "redirect:/public/schedule/event-" + event.getId();
    }

}
