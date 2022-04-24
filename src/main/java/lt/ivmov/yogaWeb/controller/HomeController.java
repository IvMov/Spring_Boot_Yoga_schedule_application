package lt.ivmov.yogaWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {
    public String getHomePage() {
        return "index";
    }

    @GetMapping(path = "/forum")
    public String getForumPage() {
        return "forum";
    }

    @GetMapping(path = "/aboutme")
    public String getAboutPage() {
        return "about";
    }

    @GetMapping(path = "/contacts")
    public String getContactsPage() {
        return "contacts";
    }

    @GetMapping(path = "/events")
    public String getEventsPage() {
        return "events";
    }

    @GetMapping(path = "/lessons")
    public String getLessonsPage() {
        return "lessons";
    }

    @GetMapping(path = "/welcome")
    public String getLoginPage() {
        return "login-page";
    }

    @GetMapping(path = "/news")
    public String getNewsPage() {
        return "news";
    }

    @GetMapping(path = "/user")
    public String getUserPage() {
        return "user-page";
    }
}
