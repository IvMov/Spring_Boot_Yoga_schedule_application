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
    private final UserService userService;
    private final ActivityService activityService;

    public EventPrivateController(EventService eventService,
                                  UserService userService,
                                  ActivityService activityService) {
        this.eventService = eventService;
        this.userService = userService;
        this.activityService = activityService;
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

    @PostMapping("/schedule/event/{id}/add-user")
    @PreAuthorize("hasRole('USER')")
    public String addUserToEvent(@PathVariable(name = "id") Long id,
                                 Authentication authentication,
                                 Model model) {

        String email = ((User) authentication.getPrincipal()).getEmail();

        User user = userService.findByEmail(email);
        Event event = eventService.findById(id);

        Activity activity = new Activity();
        activity.setStatus(ActivityStatus.WANT);
        activity.setEvent(event);
        activity.setUser(user);

        event.getActivities().add(activity);
        user.getActivities().add(activity);

        eventService.update(event);
        userService.update(user);
        activityService.update(activity);

        return "redirect:/public/schedule/event/" + event.getId();
    }

}
