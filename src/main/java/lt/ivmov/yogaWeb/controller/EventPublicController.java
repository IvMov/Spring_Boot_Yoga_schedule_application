package lt.ivmov.yogaWeb.controller;

import lombok.RequiredArgsConstructor;
import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.enums.EventTheme;
import lt.ivmov.yogaWeb.service.EventService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//all views for not authenticated users
@Controller
@RequiredArgsConstructor
@RequestMapping("/public")
public class EventPublicController {

    private final EventService eventService;


    @GetMapping("/schedule")
    public String getScheduleOfAllEvents(
            @RequestParam(name = "page", defaultValue = "0") int pageNum,
            Model model) {

        Page<Event> eventPage = eventService.findAllForPage(10, pageNum);
        List<Event> eventList = eventPage.getContent();

        model.addAttribute("events", eventList);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("maxPages", eventPage.getTotalPages());

        return "schedule";
    }

    @GetMapping("/schedule/{theme}")
    public String getScheduleByTheme(
            @PathVariable(name = "theme") EventTheme themeName,
            @RequestParam(name = "page", defaultValue = "0") int pageNum,
            Model model) {

        Page<Event> eventPageByTheme = eventService.findAllByTheme(themeName, 10, pageNum);
        List<Event> eventListByTheme = eventPageByTheme.getContent();

        model.addAttribute("events", eventListByTheme);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("maxPages", eventPageByTheme.getTotalPages());

        return "schedule";
    }

    @GetMapping("/schedule/event/{id}")
    public String getEventPage(
            @PathVariable(name = "id") Long id,
            Model model) {

        Event foundEvent = eventService.findById(id);
        model.addAttribute("event", foundEvent);

        return "event";
    }

    //TODO: planing - page with not schedule interface, but with special/unique events etc.
    @GetMapping("/events")
    public String getEventsPage() {
        return "blank";
    }

}
