package nl.reactivemoviesrest.service.user;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import nl.reactivemoviesrest.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements UserAuthenticationService {

    @Autowired
    UserService userService;

    @Override
    public Optional<String> login(final String username, final String password) {
        return userService.findByUsernameAndPassword(username, password);
    }

    @Override
    public Optional<User> findByToken(final String token) {
        return userService.find(token);
    }

}

