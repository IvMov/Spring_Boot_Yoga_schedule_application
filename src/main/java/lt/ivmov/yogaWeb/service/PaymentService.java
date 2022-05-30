package lt.ivmov.yogaWeb.service;

import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.entity.Payment;
import lt.ivmov.yogaWeb.entity.User;
import lt.ivmov.yogaWeb.enums.PaymentMethod;
import lt.ivmov.yogaWeb.enums.PaymentType;
import lt.ivmov.yogaWeb.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment create(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment addParticularPaymentByCredits(User user, Event event) {

        Payment payment = new Payment();

        payment.setUser(user);
        payment.setEvent(event);
        payment.setType(PaymentType.COST);
        payment.setMethod(PaymentMethod.CREDITS);
        payment.setSum(event.getFinalPrice());
        return payment;
    }

    public Payment addFullPaymentByCredits(User user, Event event) {

        Payment payment = new Payment();

        payment.setUser(user);
        payment.setEvent(event);
        payment.setType(PaymentType.COST);
        payment.setMethod(PaymentMethod.CREDITS);
        payment.setSum(user.getCreditsBalance());
        return payment;
    }
}
