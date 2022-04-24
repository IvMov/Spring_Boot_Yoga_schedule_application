package lt.ivmov.yogaWeb.entity;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userName;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String city;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String about;

}
