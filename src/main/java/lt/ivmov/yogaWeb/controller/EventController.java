package lt.ivmov.yogaWeb.controller;

import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.repository.EventRepository;
import lt.ivmov.yogaWeb.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

//    @PostMapping("/create")
//    public String createProduct(Product product, Model model) {
//        Product createdProduct = productService.create(product);
//
//        model.addAttribute("product", createdProduct);
//        return "redirect:/products/" + createdProduct.getId();
//    }


}
