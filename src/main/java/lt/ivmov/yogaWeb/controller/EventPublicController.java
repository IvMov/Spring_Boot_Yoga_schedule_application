package lt.ivmov.yogaWeb.controller;

import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.service.EventService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/public/schedule")
public class EventPublicController {

    private EventService eventService;

    public EventPublicController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public String getEventList(
            @RequestParam(name = "page", defaultValue = "0") int pageNum,
            Model model) {

        Page<Event> eventPage = eventService.findAllForPage(5, pageNum);
        List<Event> eventList = eventPage.getContent();

        model.addAttribute("events", eventList);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("maxPages", eventPage.getTotalPages());

        return "schedule";
    }

    @GetMapping("/{theme}")
    public String getScheduleByTheme(
            @PathVariable(name = "theme") String themeName,
            @RequestParam(name = "page", defaultValue = "0") int pageNum,
            Model model) {
        Page<Event> eventPageByTheme = eventService.findAllByTheme(themeName, 5, pageNum);
        List<Event> eventListByTheme = eventPageByTheme.getContent();
        model.addAttribute("events", eventListByTheme);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("maxPages", eventPageByTheme.getTotalPages());
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

}
