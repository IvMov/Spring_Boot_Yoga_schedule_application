package lt.ivmov.yogaWeb.repository;

import lt.ivmov.yogaWeb.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
