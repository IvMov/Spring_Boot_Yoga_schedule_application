package lt.ivmov.yogaWeb.controller;

import lt.ivmov.yogaWeb.entity.Activity;
import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.entity.Payment;
import lt.ivmov.yogaWeb.entity.User;
import lt.ivmov.yogaWeb.enums.ActivityStatus;
import lt.ivmov.yogaWeb.enums.PaymentMethod;
import lt.ivmov.yogaWeb.enums.PaymentType;
import lt.ivmov.yogaWeb.service.ActivityService;
import lt.ivmov.yogaWeb.service.EventService;
import lt.ivmov.yogaWeb.service.PaymentService;
import lt.ivmov.yogaWeb.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

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

        Activity activityWant = new Activity();
        User user = userService.findByEmail(email);
        Event event = eventService.findById(id);

        activityWant.setStatus(ActivityStatus.WANT);
        activityWant.setUser(user);
        activityWant.setEvent(event);

        user.getActivities().add(activityWant);
        event.getActivities().add(activityWant);

        user.getEventsUnpaid().add(event); //only for activity Status WANT or PARTICULARLY_PAID
        event.getUsersUnpaid().add(user); //only for activity Status WANT or PARTICULARLY_PAID

        activityService.create(activityWant);

        //check user balance -> if it is possible to pay for event
        if (user.getCreditsBalance() != null && user.getCreditsBalance() >= 0) {

            //full payment for event
            if (user.getCreditsBalance() >= event.getFinalPrice()) {
                Activity activity = new Activity();
                Payment payment = new Payment();

                user.setCreditsBalance(user.getCreditsBalance() - event.getFinalPrice());

                user.getEventsUnpaid().remove(event);
                user.getEventsPaid().add(event); //only for Activity status FULLY_PAID
                event.getUsersUnpaid().remove(user);
                event.getUsersPaid().add(user); //only for Activity status FULLY_PAID

                payment.setUser(user);
                payment.setEvent(event);
                payment.setType(PaymentType.COST);
                payment.setMethod(PaymentMethod.CREDITS);
                payment.setSum(event.getFinalPrice());

                activity.setUser(user);
                activity.setEvent(event);
                activity.setStatus(ActivityStatus.FULLY_PAID);

                activity.setPayment(paymentService.create(payment));

                activityService.create(activity);

                //particular payment for event
            } else if (user.getCreditsBalance() < event.getFinalPrice() && user.getCreditsBalance() > 0) {
                Activity activity = new Activity();
                Payment payment = new Payment();

                payment.setUser(user);
                payment.setEvent(event);
                payment.setType(PaymentType.COST);
                payment.setMethod(PaymentMethod.CREDITS);
                payment.setSum(user.getCreditsBalance());

                user.setCreditsBalance(0.00);

                activity.setUser(user);
                activity.setEvent(event);
                activity.setStatus(ActivityStatus.PARTICULARLY_PAID);


                activity.setPayment(paymentService.create(payment));

                activityService.create(activity);

            }

        }

        return "redirect:/public/schedule/event/" + event.getId();
    }

    @PostMapping("/schedule/event/{id}/remove-user")
    @PreAuthorize("hasRole('USER')")
    public String removeUserToEvent(@PathVariable(name = "id") Long id,
                                    Authentication authentication,
                                    Model model) {

        String email = ((User) authentication.getPrincipal()).getEmail();

        Activity activityCancel = new Activity();
        User user = userService.findByEmail(email);
        Event event = eventService.findById(id);

        activityCancel.setStatus(ActivityStatus.CANCELED);
        activityCancel.setUser(user);
        activityCancel.setEvent(event);

        user.getActivities().add(activityCancel);
        event.getActivities().add(activityCancel);

        if (event.getUsersPaid().contains(user)) {

            user.getEventsPaid().remove(event);
            event.getUsersPaid().remove(user);

            double sum = event.getPayments().stream()
                    .filter(payment -> payment.getUser().getId() == user.getId()) //TODO: refactor to find last by date
                    .toList().get(0).getSum();

            user.setCreditsBalance(user.getCreditsBalance() + sum);

        } else if (event.getUsersUnpaid().contains(user)) {

            user.getEventsUnpaid().remove(event);
            event.getUsersUnpaid().remove(user);

            double sum = event.getPayments().stream()
                    .filter(payment -> payment.getUser().getId() == user.getId()) //TODO: refactor to find last by date
                    .toList().get(0).getSum();

            user.setCreditsBalance(user.getCreditsBalance() + sum);

        }

        activityService.create(activityCancel);

        return "redirect:/public/schedule/event/" + event.getId();
    }


}
