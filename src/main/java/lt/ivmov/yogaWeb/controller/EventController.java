package lt.ivmov.yogaWeb.controller;

import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.enums.DaysOfWeek;
import lt.ivmov.yogaWeb.repository.EventRepository;
import lt.ivmov.yogaWeb.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.form.CheckboxesTag;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/schedule")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public String getSchedulePage(Model model) {
        List<Event> eventList = eventService.findAll();
        model.addAttribute("events", eventList);
        return "schedule";
    }

    @GetMapping("/{theme}")
    public String getScheduleByTheme(
            @PathVariable(name = "theme") String themeName,
            Model model) {
        List<Event> eventListByTheme = eventService.findAllByTheme(themeName);
        model.addAttribute("events", eventListByTheme);
        return "schedule";
    }

    @GetMapping("/event-{id}")
    public String getEventPage(
            @PathVariable(name = "id") Long id,
            Model model) {
        Event foundEvent = eventService.findById(id);
        model.addAttribute("event", foundEvent);
        return "event";
    }
     @GetMapping("/new-create")
    public String getEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "new-event";
    }

    @PostMapping("/create")
    public String createEvent(Event event, Model model) {
        Event createdEvent = eventService.create(event);
        model.addAttribute("event", createdEvent);

        return "redirect:/schedule";
    }


}
