package nl.reactivemoviesrest.service.user;

import nl.reactivemoviesrest.data.entity.User;

import java.util.Optional;

/**
 *
 */
public interface UserService {

    Optional<User> find(String id);

    Optional<String> findByUsernameAndPassword(String username, String password);

}