package lt.ivmov.yogaWeb.repository;

import lt.ivmov.yogaWeb.entity.StudentPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<StudentPayment, Long> {
}
