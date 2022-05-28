package lt.ivmov.yogaWeb.entity;


import lombok.Getter;
import lombok.Setter;
import lt.ivmov.yogaWeb.enums.UserRoles;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {
    //TODO: refactor all to customers want User - but mySql is blocked user?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private final LocalDateTime timestamp = LocalDateTime.now();

    @Column
    private String name = "Name";

    @Column
    private String surname = "Surname";

    @Column
    private String city = "Unknown";

    @Column
    private String phone = "+37060985783";

    @Column
    private String email = "lala@lala.com";

    @Column
    private String username = name + (int) ((Math.random() * 9998) + 1);

    @Column
    private String password;

    @Column
    private String passwordCheck; //temporary for password check

    @Column
    private String about;

    @Column
    private Double creditsBalance = 0.00; // 1 credit = 1 euro

    @ManyToMany(mappedBy = "usersPaid")
    private Set<Event> eventsPaid = new HashSet<>();

    @ManyToMany(mappedBy = "usersUnpaid")
    private Set<Event> eventsUnpaid = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Activity> activities = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Payment> payments = new HashSet<>();

    @ElementCollection
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Fetch(value = FetchMode.JOIN) //analog fetch EAGER
    private Set<UserRoles> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString()))
                .toList();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
