package lt.ivmov.yogaWeb.repository;

import lt.ivmov.yogaWeb.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
