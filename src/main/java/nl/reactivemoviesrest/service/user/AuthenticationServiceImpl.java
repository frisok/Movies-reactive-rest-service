package nl.reactivemoviesrest.service.user;

import lombok.AllArgsConstructor;
import nl.reactivemoviesrest.data.document.AuthenticationToken;
import nl.reactivemoviesrest.data.document.User;
import nl.reactivemoviesrest.data.repository.UserRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        final String token = UUID.randomUUID().toString();
        user.setAuthenticationToken(new AuthenticationToken(token, DateUtils.addHours(new Date(), 1)));
        userRepository.save(user).block();
        return Optional.of(token);
    }

    @Override
    public Optional<User> findByToken(final String token) {
        return Optional.ofNullable(userRepository.findByAuthenticationTokenToken(token).block());
    }

}

