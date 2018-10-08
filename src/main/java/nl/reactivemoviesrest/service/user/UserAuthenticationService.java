package nl.reactivemoviesrest.service.user;

import nl.reactivemoviesrest.data.document.User;

import java.util.Optional;

/**
 *
 */
public interface UserAuthenticationService {

    Optional<String> login(String username, String password);

    Optional<User> findByToken(String token);

}