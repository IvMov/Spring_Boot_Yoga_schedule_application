package lt.ivmov.yogaWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


//contains controllers for pages which temporary without information
@Controller
@RequestMapping(path = "/public")
public class HomeController {

    @GetMapping
    public String getHomePage(Model model) {
        String content = "This will be main page with a lot of different content, but only after backend project ends!";
        model.addAttribute("content", content);
        return "index";
    }

    //TODO: in future will do this
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
