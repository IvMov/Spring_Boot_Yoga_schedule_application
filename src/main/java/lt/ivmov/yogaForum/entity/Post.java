package lt.ivmov.yogaForum.entity;

import lombok.Getter;
import lt.ivmov.yogaForum.enums.PostTopics;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private final LocalDateTime creationDateTime = LocalDateTime.now();

    @Column
    @Enumerated(EnumType.STRING)
    private PostTopics postTopics;

    @Column
    private String post;

    @Column(nullable = false)
    @JoinColumn(name = "author_id", columnDefinition = "users", referencedColumnName = "id")
    private Long userId;

}
