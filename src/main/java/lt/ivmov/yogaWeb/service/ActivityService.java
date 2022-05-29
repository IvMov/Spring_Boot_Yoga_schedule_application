package lt.ivmov.yogaWeb.service;

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
import java.util.List;
import java.util.stream.Stream;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserService userService;


    public ActivityService(ActivityRepository activityRepository, UserService userService) {
        this.activityRepository = activityRepository;
        this.userService = userService;
    }

    public List<Activity> findAllPaid() {

        return getActivitiesPaid().stream()
                .filter(activity -> activity.getStatus() == ActivityStatus.FULLY_PAID)
                .toList();
    }

    public List<Activity> findAllUnpaid() {

        List<Activity> particularAndWant = getActivitiesParticularAndWant();

        List<Activity> particular = getActivitiesParticular(particularAndWant);

        List<Long> particularEventId = particular.stream()
                .map(activity -> activity.getEvent().getId()).toList();

        List<Activity> want = getActivitiesWant(particularAndWant, particularEventId);

        return Stream.concat(particular.stream(), want.stream()).toList();
    }

    public Activity addWantActivity(User user, Event event) {
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

    public Activity addParticularPaidActivity(PaymentService paymentService,
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

    public Activity addFullyPaidActivity(PaymentService paymentService,
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

    public Activity addConfirmFullyPaid(Payment payment,
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

    public Activity addCanceledActivity(User user, Event event) {
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

    public Activity update(Activity activity) {
        return activityRepository.save(activity);
    }

    @NotNull
    private Double findPaidPriceForParticular(User user) {
        List<Activity> activitiesParticular = getActivitiesParticular(getActivitiesParticularAndWant());

        List<Activity> activities = activitiesParticular.stream()
                .filter(activity -> activity.getPayment() != null)
                .filter(activity -> activity.getPayment().getUser().getId() == user.getId())
                .toList();
        if (activities.size() > 0) {
            return activities.get(0).getPayment().getSum();
        }
        return 0.00;
    }

    @NotNull
    private List<Activity> getActivitiesParticularAndWant() {
        return activityRepository.findAll().stream()
                .filter(activity -> !activity.getUser().getEventsUnpaid().isEmpty())
                .filter(activity -> activity.getEvent().getStartDate().isAfter(LocalDate.now()))
                .filter(activity -> activity.getEvent().getUsersUnpaid().containsAll(userService.findAllUsersUnpaid()))
                .toList();
    }

    @NotNull
    private List<Activity> getActivitiesPaid() {
        return activityRepository.findAll().stream()
                .filter(activity -> !activity.getUser().getEventsPaid().isEmpty())
                .filter(activity -> activity.getEvent().getStartDate().isAfter(LocalDate.now()))
                .filter(activity -> activity.getEvent().getUsersPaid().containsAll(userService.findAllUsersPaid()))
                .toList();
    }

    @NotNull
    private List<Activity> getActivitiesParticular(List<Activity> particularAndWant) {
        return particularAndWant.stream()
                .filter(activity -> activity.getStatus() == ActivityStatus.PARTICULARLY_PAID)
                .toList();
    }

    @NotNull
    private List<Activity> getActivitiesWant(List<Activity> particularAndWant,
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
}
