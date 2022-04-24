package lt.ivmov.yogaWeb.repository;

import lt.ivmov.yogaWeb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
