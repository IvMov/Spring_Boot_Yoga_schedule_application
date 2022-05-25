package lt.ivmov.yogaWeb.service;

import lt.ivmov.yogaWeb.entity.Payment;
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

    public Payment update(Payment payment) {
        return paymentRepository.save(payment);
    }
}
