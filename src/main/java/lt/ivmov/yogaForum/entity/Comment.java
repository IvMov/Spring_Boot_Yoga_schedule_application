package lt.ivmov.yogaForum.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "comments")
public class Comment {

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
