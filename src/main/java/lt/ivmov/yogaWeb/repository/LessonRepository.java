package lt.ivmov.yogaWeb.repository;

import lt.ivmov.yogaWeb.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
