package lt.ivmov.yogaWeb.service;

import lombok.RequiredArgsConstructor;
import lt.ivmov.yogaWeb.entity.Activity;
import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.entity.Payment;
import lt.ivmov.yogaWeb.entity.User;
import lt.ivmov.yogaWeb.enums.ActivityStatus;
import lt.ivmov.yogaWeb.enums.PaymentType;
import lt.ivmov.yogaWeb.repository.ActivityRepository;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserService userService;
    private final EventService eventService;

    public Activity addWantStatus(User user, Event event) {
        Activity activity = new Activity();
        activity.setStatus(ActivityStatus.WANT);
        activity.setUser(user);
        activity.setEvent(event);

        user.getActivities().add(activity);
        event.getActivities().add(activity);

        user.getEventsUnpaid().add(event);
        event.getUsersUnpaid().add(user);

        return activity;
    }

    public Activity addParticularPaidStatus(PaymentService paymentService,
                                            User user,
                                            Event event) {
        Activity activity = new Activity();
        Payment payment = paymentService.addFullPaymentByCredits(user, event);

        user.setCreditsBalance(0.00);

        activity.setUser(user);
        activity.setEvent(event);
        activity.setStatus(ActivityStatus.PARTICULARLY_PAID);

        activity.setPayment(paymentService.create(payment));
        return activity;
    }

    public Activity addFullyPaidStatus(PaymentService paymentService,
                                       User user,
                                       Event event) {
        Activity activity = new Activity();
        Payment payment = paymentService.addParticularPaymentByCredits(user, event);

        user.setCreditsBalance(user.getCreditsBalance() - event.getFinalPrice());

        user.getEventsUnpaid().remove(event);
        user.getEventsPaid().add(event);

        event.getUsersUnpaid().remove(user);
        event.getUsersPaid().add(user);

        activity.setUser(user);
        activity.setEvent(event);
        activity.setStatus(ActivityStatus.FULLY_PAID);

        activity.setPayment(paymentService.create(payment));
        return activity;
    }

    public Activity confirmFullyPaidStatus(Payment payment,
                                           PaymentService paymentService,
                                           User user,
                                           Event event) {

        Activity activity = new Activity();

        user.getEventsUnpaid().remove(event);
        user.getEventsPaid().add(event);

        event.getUsersUnpaid().remove(user);
        event.getUsersPaid().add(user);

        payment.setEvent(event);
        payment.setUser(user);
        payment.setType(PaymentType.COST);

        activity.setEvent(event);
        activity.setUser(user);
        activity.setStatus(ActivityStatus.FULLY_PAID);

        activity.setPayment(paymentService.create(payment));

        return activity;
    }

    public Activity addRefillCreditsStatus(Payment payment,
                                           PaymentService paymentService,
                                           User user) {

        Activity activity = new Activity();

        user.setCreditsBalance(user.getCreditsBalance() + payment.getSum());

        payment.setUser(user);
        payment.setType(PaymentType.INCOME);

        activity.setUser(user);
        activity.setStatus(ActivityStatus.REFILL_CREDITS);

        activity.setPayment(paymentService.create(payment));

        return activity;
    }

    public Activity addCanceledStatus(User user, Event event) {
        Activity activity = new Activity();

        activity.setStatus(ActivityStatus.CANCELED);
        activity.setUser(user);
        activity.setEvent(event);

        user.getActivities().add(activity);
        event.getActivities().add(activity);

        if (event.getUsersPaid().contains(user)) {

            user.getEventsPaid().remove(event);
            event.getUsersPaid().remove(user);

            user.setCreditsBalance(user.getCreditsBalance() + event.getFinalPrice());

        } else if (event.getUsersUnpaid().contains(user)) {

            user.getEventsUnpaid().remove(event);
            event.getUsersUnpaid().remove(user);

            user.setCreditsBalance(user.getCreditsBalance() + findPaidPriceForParticular(user));
        }
        return activity;
    }

    public Activity create(Activity activity) {
        return activityRepository.save(activity);
    }

    public List<Activity> findAllPaid() {

        return getPaidActivities();
    }

    public List<Activity> findAllUnpaid() {

        return getUnpaidActivities();
    }


    @NotNull
    private List<Activity> filterPaidActivities() {
        return activityRepository.findAll().stream()
                .filter(activity -> activity.getStatus() != ActivityStatus.REFILL_CREDITS)
                .filter(activity -> !activity.getUser().getEventsPaid().isEmpty())
                .filter(activity -> activity.getEvent().getStartDate().isAfter(LocalDate.now()))
                .filter(activity -> activity.getStatus() == ActivityStatus.FULLY_PAID)
                .toList();
    }

    @NotNull
    private List<Activity> getPaidActivities() {
        List<Activity> activities = filterPaidActivities();
        Set<User> allPaidUsers = eventService.findAllPaidUsers();
        Set<Event> allPaidEvents = userService.findAllEventsPaid();

        List<Activity> paidActivities = new ArrayList<>();

        for (int i = 0; i < activities.size(); i++) {
            for (User user : allPaidUsers) {
                for (Event event : allPaidEvents) {
                    if ((activities.get(i).getUser() == user) && (activities.get(i).getEvent() == event))
                        paidActivities.add(activities.get(i));
                }
            }
        }

        return paidActivities;
    }

    @NotNull
    private List<Activity> filterWantAndParticularActivities() {
        return activityRepository.findAll().stream()
                .filter(activity -> activity.getStatus() != ActivityStatus.REFILL_CREDITS)
                .filter(activity -> !activity.getUser().getEventsUnpaid().isEmpty())
                .filter(activity -> activity.getEvent().getStartDate().isAfter(LocalDate.now()))
                .filter(activity -> activity.getStatus() == ActivityStatus.PARTICULARLY_PAID || activity.getStatus() == ActivityStatus.WANT)
                .toList();
    }

    @NotNull
    private List<Activity> getWantAndParticularUnpaid() {
        List<Activity> activities = filterWantAndParticularActivities();
        Set<User> allPaidUsers = eventService.findAllUnpaidUsers();
        Set<Event> allPaidEvents = userService.findAllEventsUnpaid();

        List<Activity> unpaidActivities = new ArrayList<>();

        for (int i = 0; i < activities.size(); i++) {
            for (User user : allPaidUsers) {
                for (Event event : allPaidEvents) {
                    if ((activities.get(i).getUser() == user) && (activities.get(i).getEvent() == event))
                        unpaidActivities.add(activities.get(i));
                }
            }
        }

        return unpaidActivities;
    }

    @NotNull
    private List<Activity> separateWantActivities(List<Activity> particularAndWant,
                                                  List<Long> particularEventId) {

        List<Activity> want = particularAndWant.stream()
                .filter(activity -> activity.getStatus() == ActivityStatus.WANT).toList();

        for (Long id : particularEventId) {
            want = want.stream()
                    .filter(activity -> activity.getEvent().getId() != id)
                    .toList();
        }
        return want;
    }

    @NotNull
    private List<Activity> separateParticularActivities(List<Activity> particularAndWant) {
        return particularAndWant.stream()
                .filter(activity -> activity.getStatus() == ActivityStatus.PARTICULARLY_PAID)
                .toList();
    }

    @NotNull
    private List<Activity> getUnpaidActivities() {
        List<Activity> unpaidActivities = getWantAndParticularUnpaid();

        List<Activity> particular = separateParticularActivities(unpaidActivities);

        List<Long> particularEventId = particular.stream()
                .map(activity -> activity.getEvent().getId()).toList();

        List<Activity> want = separateWantActivities(unpaidActivities, particularEventId);

        return Stream.concat(particular.stream(), want.stream()).toList();
    }

    @NotNull
    private Double findPaidPriceForParticular(User user) {
        List<Activity> activitiesParticular = separateParticularActivities(filterWantAndParticularActivities());

        List<Activity> activities = activitiesParticular.stream()
                .filter(activity -> activity.getPayment() != null)
                .filter(activity -> activity.getPayment().getUser().getId() == user.getId())
                .toList();
        if (activities.size() > 0) {
            return activities.get(0).getPayment().getSum();
        }
        return 0.00;
    }
}
