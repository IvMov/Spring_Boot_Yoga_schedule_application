package lt.ivmov.yogaWeb.repository;

import lt.ivmov.yogaWeb.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

@Deprecated(since = "2022-04-23")
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
