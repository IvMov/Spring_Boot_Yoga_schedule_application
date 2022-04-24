package lt.ivmov.yogaWeb.repository;

import lt.ivmov.yogaWeb.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
