package nl.reactivemoviesrest.web.controller;

import nl.reactivemoviesrest.data.UsernamePassword;
import nl.reactivemoviesrest.service.user.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 *
 */
@RestController
public class LoginController {

    @Autowired
    private UserAuthenticationService authentication;


    /**
     * Example request: curl -XPOST -d 'username=admin&password=admin' http://localhost:8080/login
     */
   /* @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    String login(@RequestParam("username") final String username,
                 @RequestParam("password") final String password) {

        return authentication
                .login(username, password)
                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
    }*/

    /**
     * Example request: curl -H "Content-Type: application/json" -XPOST -d '{"username":"admin","password":"admin"}' http://localhost:8080/login
     */
    @PostMapping("/login")
    @CrossOrigin(origins = {"http://localhost","http://localhost:4200","http://85.214.209.74"})
    ResponseEntity<String> login(@RequestBody final UsernamePassword usernamePassword) {

        final Optional<String> result = authentication
                .login(usernamePassword.getUsername(), usernamePassword.getPassword());

        return new ResponseEntity(result, HttpStatus.OK);
    }

}