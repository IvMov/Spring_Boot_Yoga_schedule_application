package lt.ivmov.yogaWeb.controller;

import lombok.RequiredArgsConstructor;
import lt.ivmov.yogaWeb.entity.Activity;
import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.entity.User;
import lt.ivmov.yogaWeb.service.ActivityService;
import lt.ivmov.yogaWeb.service.EventService;
import lt.ivmov.yogaWeb.service.PaymentService;
import lt.ivmov.yogaWeb.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//control adding and removing users to events
@Controller
@RequiredArgsConstructor
@RequestMapping("/private")
public class PrivateActivityController {

    private final EventService eventService;
    private final UserService userService;
    private final ActivityService activityService;
    private final PaymentService paymentService;


    @PostMapping("/schedule/event/{id}/add-user")
    @PreAuthorize("hasRole('USER')")
    public String addUserToEvent(@PathVariable(name = "id") Long id,
                                 Authentication authentication) {

        User user = getAuthenticatedUser(authentication);
        Event event = eventService.findById(id);

        activityService.create(activityService.addWantStatus(user, event));

        //check user balance -> if it is possible to pay for event
        if (user.getCreditsBalance() != null && user.getCreditsBalance() >= 0) {

            //full payment for event
            if (user.getCreditsBalance() >= event.getFinalPrice()) {

                Activity activityFullyPaid = activityService.addFullyPaidStatus(paymentService, user, event);
                activityService.create(activityFullyPaid);

                //particular payment for event
            } else if (user.getCreditsBalance() < event.getFinalPrice() && user.getCreditsBalance() > 0) {

                Activity activity = activityService.addParticularPaidStatus(paymentService, user, event);
                activityService.create(activity);
            }
        }
        return "redirect:/public/schedule";
    }

    //authenticated user can cancel his participation in event
    @PostMapping("/schedule/event/{id}/remove-user")
    @PreAuthorize("hasRole('USER')")
    public String removeUserFromEvent(@PathVariable(name = "id") Long id,
                                      Authentication authentication) {

        User user = getAuthenticatedUser(authentication);
        Event event = eventService.findById(id);

        Activity activityCancel = activityService.addCanceledStatus(user, event);

        activityService.create(activityCancel);

        return "redirect:/public/schedule";
    }

    //admin can cancel any user participation in event
    @PostMapping("/schedule/event/{eId}/remove-user/{uId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String removeUserFromEvent(@PathVariable(name = "eId") Long eId,
                                      @PathVariable(name = "uId") Long uId) {

        User user = userService.findById(uId);
        Event event = eventService.findById(eId);

        Activity activityCancel = activityService.addCanceledStatus(user, event);

        activityService.create(activityCancel);

        return "redirect:/private/schedule/paid-and-unpaid";
    }

    private User getAuthenticatedUser(Authentication authentication) {
        String email = ((User) authentication.getPrincipal()).getEmail();

        return userService.findByEmail(email);
    }

}
