package lt.ivmov.yogaForum.repository;

import lt.ivmov.yogaForum.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
