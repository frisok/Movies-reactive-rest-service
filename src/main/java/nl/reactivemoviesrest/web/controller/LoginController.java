package nl.reactivemoviesrest.web.controller;

import nl.reactivemoviesrest.service.user.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class LoginController {

    @Autowired
    private UserAuthenticationService authentication;


    /**
     *
     * Example request: curl -XPOST -d 'username=admin&password=admin' http://localhost:8080/login
     */
    @PostMapping("/login")
    String login(
            @RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        return authentication
                .login(username, password)
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }

}