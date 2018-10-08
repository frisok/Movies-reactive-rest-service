package nl.reactivemoviesrest.service.user;

import lombok.AllArgsConstructor;
import nl.reactivemoviesrest.data.document.User;
import nl.reactivemoviesrest.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements UserAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<String> login(final String username, final String password) {

        final Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsernameAndPassword(username, password).block());

        if (userOptional.isPresent()) {
            return createTokenAndUpdateUser(userOptional.get());
        } else {
            return Optional.empty();
        }
    }

    private Optional<String> createTokenAndUpdateUser(User user) {
        final String authenticationToken = UUID.randomUUID().toString();
        user.setAuthenticationToken(authenticationToken);
        userRepository.save(user).block();
        return Optional.of(authenticationToken);
    }

    @Override
    public Optional<User> findByToken(final String token) {
        return Optional.ofNullable(userRepository.findByAuthenticationToken(token).block());
    }

}

