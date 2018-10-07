package nl.reactivemoviesrest.data.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 */
@Getter
@Setter
@Builder
public class User implements UserDetails {


    private String id;
    private String username;
    private String password;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired =true;
    private boolean enabled = true;
    private Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

}