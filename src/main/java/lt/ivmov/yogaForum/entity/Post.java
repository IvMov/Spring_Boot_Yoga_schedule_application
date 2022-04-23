package lt.ivmov.yogaForum.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
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
    private String post;

    @Column(nullable = false)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Long userId;

}
