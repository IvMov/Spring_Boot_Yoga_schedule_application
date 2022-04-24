package lt.ivmov.yogaWeb.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "comments")
@Deprecated(since = "2022-04-23") //Class temporary deprecated - reason lack of time to do now
public class Comment { //TODO: after VIGI want to add forum page with questions/posts and comments from users

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private final LocalDateTime creationDateTime = LocalDateTime.now();

    @Column(nullable = false)
    @JoinColumn(name = "author_id", columnDefinition = "users", referencedColumnName = "id")
    private Long userId;

    @Column(nullable = false)
    @JoinColumn(name = "post_id", columnDefinition = "posts", referencedColumnName = "id")
    private Long postId;

    @Column
    private String comment;

}
