package lt.ivmov.yogaWeb.controller;

import lombok.RequiredArgsConstructor;
import lt.ivmov.yogaWeb.entity.Activity;
import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.entity.Payment;
import lt.ivmov.yogaWeb.entity.User;
import lt.ivmov.yogaWeb.service.ActivityService;
import lt.ivmov.yogaWeb.service.EventService;
import lt.ivmov.yogaWeb.service.PaymentService;
import lt.ivmov.yogaWeb.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


//Payment confirmation and user credits balance refilling
@Controller
@RequiredArgsConstructor
@RequestMapping("/private")
public class PrivatePaymentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final EventService eventService;
    private final ActivityService activityService;


    //admin on user-page can refill balance of any user
    @GetMapping("payment/user/{id}/add-credits/form")
    @PreAuthorize("hasRole('ADMIN')")
    public String getRefillUserCredits(@PathVariable(name = "id") Long id,
                                       Model model) {

        User user = userService.findById(id);

        model.addAttribute("payment", new Payment());
        model.addAttribute("user", user);

        return "income";
    }

    @PostMapping("/payment/user/{id}/add-credits")
    @PreAuthorize("hasRole('ADMIN')")
    public String refillUserCredits(@PathVariable(name = "id") Long id,
                                    Payment payment) {

        User user = userService.findById(id);

        Activity activity = activityService.addRefillCreditsStatus(payment, paymentService, user);
        activityService.create(activity);

        return "redirect:/private/user/" + user.getUsername();
    }

    //open page where admin can confirm that got payment, "ntp" -> need to pay sum
    @GetMapping("/payment/confirm/event/{eId}/user/{uId}/{ntp}")
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

    @PostMapping("/payment/confirm/{eId}/{uId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String confirmPayment(@PathVariable(name = "eId") Long eId,
                                 @PathVariable(name = "uId") Long uId,
                                 Payment payment) {

        User user = userService.findById(uId);
        Event event = eventService.findById(eId);

        Activity activity = activityService.confirmFullyPaidStatus(payment, paymentService, user, event);
        activityService.create(activity);

        return "redirect:/private/schedule/paid-and-unpaid";
    }
}
