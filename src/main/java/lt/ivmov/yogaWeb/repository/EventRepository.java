package lt.ivmov.yogaWeb.repository;

import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.enums.EventTheme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findAllByTheme(EventTheme theme, Pageable pageable);
}
