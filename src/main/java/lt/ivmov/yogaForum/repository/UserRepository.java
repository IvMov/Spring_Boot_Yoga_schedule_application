package lt.ivmov.yogaForum.repository;

import lt.ivmov.yogaForum.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
