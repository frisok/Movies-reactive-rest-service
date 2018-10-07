package nl.reactivemoviesrest.service.user;

import nl.reactivemoviesrest.data.entity.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

/**
 * In memory implementatio of UserService
 */
@Service
final class InMemoryUserService implements UserService {

    private static Map<String, User> users = new HashMap<>();


    static {
        final String id1 = UUID.randomUUID().toString();
        users.put(id1, User.builder()
                .id(id1)
                .username("admin")
                .password("admin")
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build());

        final String id2 = UUID.randomUUID().toString();
        users.put(id2, User.builder()
                .id(id2)
                .username("user")
                .password("user")
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build());
    }

    @Override
    public Optional<User> find(final String id) {
        return ofNullable(users.get(id));
    }

    @Override
    public Optional<String> findByUsernameAndPassword(final String username, final String password) {
        return users
                .values()
                .stream()
                .filter(u -> Objects.equals(username, u.getUsername()))
                .filter(u -> Objects.equals(password, u.getPassword()))
                .map(u -> u.getId())
                .findFirst();
    }

}