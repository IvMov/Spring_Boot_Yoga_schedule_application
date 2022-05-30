package lt.ivmov.yogaWeb.controller;

import lombok.RequiredArgsConstructor;
import lt.ivmov.yogaWeb.entity.Activity;
import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.service.ActivityService;
import lt.ivmov.yogaWeb.service.EventService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//creating/changing/deleting of events by user. Admin schedule page
@Controller
@RequiredArgsConstructor
@RequestMapping("/private")
public class EventPrivateController {

    private final EventService eventService;
    private final ActivityService activityService;

    @GetMapping("/schedule/paid-and-unpaid")
    @PreAuthorize("hasRole('ADMIN')")
    public String getPaidAndUnpaidEventsAndUsers(Model model) {

        List<Activity> activitiesPaid = activityService.findAllPaid();
        List<Activity> activitiesUnpaid = activityService.findAllUnpaid();

        model.addAttribute("activitiesPaid", activitiesPaid);
        model.addAttribute("activitiesUnpaid", activitiesUnpaid);

        return "admin-schedule";
    }

    @GetMapping("/schedule/event/{id}")
    @PreAuthorize("hasRole('USER')")
    public String getEventPage(
            @PathVariable(name = "id") Long id,
            Model model) {

        Event foundEvent = eventService.findById(id);

        model.addAttribute("event", foundEvent);
        return "event";
    }

    @GetMapping("/schedule/event/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEventCreateForm(Model model) {
        model.addAttribute("event", new Event());
        return "new-event";
    }

    @PostMapping("/schedule/event/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createEvent(Event event) {

        if (event.getIsRepeatable()) {
            //create in the nearest 90 days events by daysOfWeek
            eventService.createEventsByWeekDays(event, eventService);
        } else {
            event.setFinalPrice(event.getFinalPriceWithDiscount());
            eventService.create(event);
        }
        return "redirect:/public/schedule";
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
    public String deleteEventById(@PathVariable(name = "id") Long id) {

        eventService.delete(eventService.findById(id));

        return "redirect:/public/schedule";
    }


}
