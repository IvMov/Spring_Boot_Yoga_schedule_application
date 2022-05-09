package lt.ivmov.yogaWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/public")
public class HomeController {

    @GetMapping
    public String getHomePage(Model model) {
        String content = "This will be main page with a lot of different content, but only after backend project ends!";
        model.addAttribute("content", content);
        return "index";
    }

    @GetMapping(path = "/lessons")
    public String getLessonsPage(Model model) {
        String h1Text = "SOME TITLE OF LESSONS schedule SOON";
        model.addAttribute("content", h1Text);
        return "blank";
    }

    @GetMapping(path = "/user")
    public String getUserPage(Model model) {
        String h1Text = "SOME TITLE OF User page SOON";
        model.addAttribute("content", h1Text);
        return "blank";
    }


    //TODO: in future will do this
    @GetMapping(path = "/news")
    public String getNewsPage(Model model) {
        String h1Text = "SOME TITLE OF NEWS NOT SOON";
        model.addAttribute("content", h1Text);
        return "blank";
    }

    @GetMapping(path = "/forum")
    public String getForumPage(Model model) {
        String h1Text = "SOME TITLE OF FORUM NOT SOON?";
        model.addAttribute("content", h1Text);
        return "blank";
    }

    @GetMapping(path = "/aboutme")
    public String getAboutPage(Model model) {
        String h1Text = "SOME TITLE OF ABOUT ME NOT SOON";
        model.addAttribute("content", h1Text);
        return "blank";
    }

    @GetMapping(path = "/contacts")
    public String getContactsPage(Model model) {
        String h1Text = "SOME TITLE OF CONTACTS NOT SOON";
        model.addAttribute("content", h1Text);
        return "blank";
    }
}
