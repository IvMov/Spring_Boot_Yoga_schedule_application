package lt.ivmov.yogaWeb.repository;

import lt.ivmov.yogaWeb.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
