package lt.ivmov.yogaWeb.entity;

import lombok.Getter;
import lt.ivmov.yogaWeb.enums.PostTopics;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Deprecated(since = "2022-04-23") //Class temporary deprecated - reason lack of time to do now
public class Post { //TODO: after VIGI want to add forum page with questions/posts and comments from users

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
