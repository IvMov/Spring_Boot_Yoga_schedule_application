package lt.ivmov.yogaWeb.service;

import lt.ivmov.yogaWeb.entity.User;
import lt.ivmov.yogaWeb.enums.UserRoles;
import lt.ivmov.yogaWeb.exception.UserNotFoundException;
import lt.ivmov.yogaWeb.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<User> findAllUsersPaid() {

        return userRepository.findAll().stream()
                .filter(user -> !user.getEventsPaid().isEmpty())
                .collect(Collectors.toList());
    }

    public List<User> findAllUsersUnpaid() {

        return userRepository.findAll().stream()
                .filter(user -> !user.getEventsUnpaid().isEmpty())
                .collect(Collectors.toList());
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

    public User update(User user) {
        return userRepository.save(user);
    }


}
