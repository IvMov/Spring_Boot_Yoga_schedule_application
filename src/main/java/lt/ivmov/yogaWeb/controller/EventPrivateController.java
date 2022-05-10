package lt.ivmov.yogaWeb.controller;

import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.service.EventService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/private/schedule")
public class EventPrivateController {

    private EventService eventService;

    public EventPrivateController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/new-event")
    public String getEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "new-event";
    }

    @PostMapping("/create")
    public String createEvent(Event event, Model model) {
        Event createdEvent = eventService.create(event);
        model.addAttribute("event", createdEvent);

        return "redirect:/public/schedule";
    }

}
