package lunna.school.security;

import lombok.Getter;
import lombok.Setter;
import lunna.school.model.Organization;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 25. Jun 2021 10:55 PM
 **/
@Getter
@Setter
public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final boolean active;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Organization organization;



    public CustomUserDetails(String username, String password, boolean active, Collection<? extends GrantedAuthority> authorities,
                             Organization organization) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.authorities = authorities;
        this.organization = organization;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

}
