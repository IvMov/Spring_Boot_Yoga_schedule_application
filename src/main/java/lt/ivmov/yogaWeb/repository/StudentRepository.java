package lt.ivmov.yogaWeb.repository;

import lt.ivmov.yogaWeb.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
