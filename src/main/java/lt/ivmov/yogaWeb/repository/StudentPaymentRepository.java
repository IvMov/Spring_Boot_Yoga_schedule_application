package lt.ivmov.yogaWeb.repository;

import lt.ivmov.yogaWeb.entity.StudentPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentPaymentRepository extends JpaRepository<StudentPayment, Long> {
}
