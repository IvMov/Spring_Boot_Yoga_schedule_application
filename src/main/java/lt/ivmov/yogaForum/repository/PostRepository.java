package lt.ivmov.yogaForum.repository;

import lt.ivmov.yogaForum.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
