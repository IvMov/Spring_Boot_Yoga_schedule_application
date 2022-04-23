package lt.ivmov.yogaForum.controller;

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
}
