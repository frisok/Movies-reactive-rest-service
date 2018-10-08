package nl.reactivemoviesrest.data.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 */
@Getter
@Setter
@Document(collection = "user")
public class User implements UserDetails {

    //db.user.insert({username:"admin", password:"admin",accountNonExpired:true,accountNonLocked:true,credentialsNonExpired:true,enabled:true,authenticationToken:""});

    @Id
    private String id;
    private String username;
    private String password;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    private Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
    private String authenticationToken;

}