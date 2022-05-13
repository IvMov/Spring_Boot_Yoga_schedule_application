package lt.ivmov.yogaWeb.controller;

import lt.ivmov.yogaWeb.entity.User;
import lt.ivmov.yogaWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/new-user")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/signin")
    public String registrateNewUser(User user, Model model) {
        User newUser = userService.create(user);
        model.addAttribute("user", newUser);
        return "redirect:/login";
    }
}
