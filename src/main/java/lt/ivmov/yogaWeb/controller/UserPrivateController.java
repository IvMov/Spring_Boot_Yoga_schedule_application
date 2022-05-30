package lt.ivmov.yogaWeb.controller;

import lombok.RequiredArgsConstructor;
import lt.ivmov.yogaWeb.entity.Activity;
import lt.ivmov.yogaWeb.entity.User;
import lt.ivmov.yogaWeb.service.ActivityService;
import lt.ivmov.yogaWeb.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//user page controller
@Controller
@RequiredArgsConstructor
@RequestMapping("/private")
public class UserPrivateController {

    private final UserService userService;
    private final ActivityService activityService;

    @GetMapping("/user/{name}")
    @PreAuthorize("hasRole('USER')")
    public String getUserPage(@PathVariable(name = "name") String name,
                              Model model) {
        User user = userService.findByUsername(name);

        List<Activity> activitiesPaid = activityService.findAllPaid().stream()
                .filter(activity -> activity.getUser().getUsername().equals(name))
                .toList();

        List<Activity> activitiesUnpaid = activityService.findAllUnpaid().stream()
                .filter(activity -> activity.getUser().getUsername().equals(name))
                .toList();

        model.addAttribute("user", user);
        model.addAttribute("activitiesPaid", activitiesPaid);
        model.addAttribute("activitiesUnpaid", activitiesUnpaid);

        return "user";
    }

}
