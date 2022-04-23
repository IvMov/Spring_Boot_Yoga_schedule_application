package lt.ivmov.yogaForum.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private final LocalDateTime creationDateTime = LocalDateTime.now();

    @Column(nullable = false)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Long userId;

    @Column(nullable = false)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Long postId;

    @Column
    private String comment;

}
