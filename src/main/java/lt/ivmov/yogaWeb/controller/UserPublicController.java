package lt.ivmov.yogaWeb.controller;

import lt.ivmov.yogaWeb.entity.User;
import lt.ivmov.yogaWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//new user registration
@Controller
@RequestMapping("/public")
public class UserPublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/new")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("user/register")
    public String registerNewUser(User user, Model model) {
        user.setUsername(user.getName() + (int) ((Math.random() * 9998) + 1));
        User newUser = userService.create(user);
        model.addAttribute("user", newUser);
        return "redirect:/login";
    }
}
