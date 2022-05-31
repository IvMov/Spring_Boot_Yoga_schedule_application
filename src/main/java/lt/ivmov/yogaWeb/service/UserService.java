package lt.ivmov.yogaWeb.service;

import lt.ivmov.yogaWeb.entity.Event;
import lt.ivmov.yogaWeb.entity.User;
import lt.ivmov.yogaWeb.enums.UserRoles;
import lt.ivmov.yogaWeb.exception.UserNotFoundException;
import lt.ivmov.yogaWeb.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with email: " + email + " not exist"));
    }

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(UserRoles.USER);
        return userRepository.save(user);
    }

    public Set<Event> findAllEventsPaid() {

        Set<Event> allEvents = new HashSet<>();

        List<User> users = userRepository.findAll().stream()

                .filter(event -> !event.getEventsPaid().isEmpty())
                .toList();

        for (User user : users) {
            allEvents.addAll(user.getEventsPaid());
        }
        return allEvents;
    }

    public Set<Event> findAllEventsUnpaid() {

        Set<Event> allEvents = new HashSet<>();

        List<User> users = userRepository.findAll().stream()
                .filter(event -> !event.getEventsUnpaid().isEmpty())
                .toList();

        for (User user : users) {
            allEvents.addAll(user.getEventsUnpaid());
        }
        return allEvents;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }


}
