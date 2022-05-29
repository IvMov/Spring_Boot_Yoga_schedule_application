package lt.ivmov.yogaWeb.controller;

import lt.ivmov.yogaWeb.entity.Activity;
import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.entity.Payment;
import lt.ivmov.yogaWeb.entity.User;
import lt.ivmov.yogaWeb.enums.ActivityStatus;
import lt.ivmov.yogaWeb.enums.PaymentType;
import lt.ivmov.yogaWeb.service.ActivityService;
import lt.ivmov.yogaWeb.service.EventService;
import lt.ivmov.yogaWeb.service.PaymentService;
import lt.ivmov.yogaWeb.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.Banner;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/private")
public class PrivateActivityController {

    private final EventService eventService;
    private final UserService userService;
    private final ActivityService activityService;
    private final PaymentService paymentService;

    public PrivateActivityController(EventService eventService,
                                     UserService userService,
                                     ActivityService activityService,
                                     PaymentService paymentService) {
        this.eventService = eventService;
        this.userService = userService;
        this.activityService = activityService;
        this.paymentService = paymentService;
    }

    @PostMapping("/schedule/event/{id}/add-user")
    @PreAuthorize("hasRole('USER')")
    public String addUserToEvent(@PathVariable(name = "id") Long id,
                                 Authentication authentication,
                                 Model model) {

        String email = ((User) authentication.getPrincipal()).getEmail();

        User user = userService.findByEmail(email);
        Event event = eventService.findById(id);

        Activity activityWant = activityService.addWantActivity(user, event);

        activityService.create(activityWant);

        //check user balance -> if it is possible to pay for event
        if (user.getCreditsBalance() != null && user.getCreditsBalance() >= 0) {

            //full payment for event
            if (user.getCreditsBalance() >= event.getFinalPrice()) {

                Activity activityFullyPaid = activityService.addFullyPaidActivity(paymentService, user, event);

                activityService.create(activityFullyPaid);

                //particular payment for event
            } else if (user.getCreditsBalance() < event.getFinalPrice() && user.getCreditsBalance() > 0) {

                Activity activity = activityService.addParticularPaidActivity(paymentService, user, event);

                activityService.create(activity);
            }
        }
        return "redirect:/public/schedule";
    }


    @PostMapping("/schedule/event/{id}/remove-user")
    @PreAuthorize("hasRole('USER')")
    public String removeUserFromEvent(@PathVariable(name = "id") Long id, Authentication authentication, Model model) {

        String userEmail = ((User) authentication.getPrincipal()).getEmail();

        User user = userService.findByEmail(userEmail);
        Event event = eventService.findById(id);

        Activity activityCancel = activityService.addCanceledActivity(user, event);

        activityService.create(activityCancel);

        return "redirect:/public/schedule";
    }


    @PostMapping("/schedule/event/{eId}/remove-user/{uId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String removeUserFromEvent(@PathVariable(name = "eId") Long eId,
                                      @PathVariable(name = "uId") Long uId,
                                      Model model) {

        User user = userService.findById(uId);
        Event event = eventService.findById(eId);

        Activity activityCancel = activityService.addCanceledActivity(user, event);

        activityService.create(activityCancel);

        return "redirect:/private/schedule/paid-and-unpaid";
    }

    @GetMapping("/confirm/event/{eId}/user/{uId}/{ntp}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getConfirmPaymentPage(@PathVariable(name = "eId") Long eId,
                                        @PathVariable(name = "uId") Long uId,
                                        @PathVariable(name = "ntp") Double ntp,
                                        Model model) {

        User user = userService.findById(uId);
        Event event = eventService.findById(eId);

        model.addAttribute("payment", new Payment());
        model.addAttribute("user", user);
        model.addAttribute("event", event);
        model.addAttribute("ntp", ntp);

        return "confirmation";
    }

    @PostMapping("/confirm/payment/{eId}/{uId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String ConfirmPayment(@PathVariable(name = "eId") Long eId,
                                 @PathVariable(name = "uId") Long uId,
                                 Payment payment,
                                 Model model) {

        User user = userService.findById(uId);
        Event event = eventService.findById(eId);

        Activity activity = activityService.addConfirmFullyPaid(payment, paymentService, user, event);
        activityService.create(activity);

        return "redirect:/private/schedule/paid-and-unpaid";
    }


    @GetMapping("/schedule/paid-and-unpaid")
    @PreAuthorize("hasRole('ADMIN')")
    public String getPaidActivitiesUsers(Model model) {

        List<Activity> activitiesPaid = activityService.findAllPaid();
        List<Activity> activitiesUnpaid = activityService.findAllUnpaid();

        model.addAttribute("activitiesPaid", activitiesPaid);
        model.addAttribute("activitiesUnpaid", activitiesUnpaid);

        return "admin-schedule";
    }

    @GetMapping("/user/{name}")
    @PreAuthorize("hasRole('USER')")
    public String getUserPageInfo(@PathVariable(name = "name") String name,
                                  Model model) {
        User user = userService.findByUsername(name);
        List<Activity> activitiesPaid = activityService.findAllPaid();
        List<Activity> activitiesUnpaid = activityService.findAllUnpaid();

        model.addAttribute("user", user);
        model.addAttribute("activitiesPaid", activitiesPaid);
        model.addAttribute("activitiesUnpaid", activitiesUnpaid);

        return "user";
    }


}
