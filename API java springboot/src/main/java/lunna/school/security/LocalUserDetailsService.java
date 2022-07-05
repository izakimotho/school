package lunna.school.security;

import lunna.school.model.Permission;
import lunna.school.model.Role;
import lunna.school.model.User;
import lunna.school.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 25. Jun 2021 11:03 PM
 **/
@Component
public class LocalUserDetailsService implements UserDetailsService {

    private final  UserRepository userRepository;

    public LocalUserDetailsService(UserRepository userAccountRepository) {
        this.userRepository = userAccountRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username [" + username + "] not found in the system");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();

        for (Role userRole : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getRole_name()));
            for (Permission permission : userRole.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission.getPermission_name()));
            }
        }


        return new CustomUserDetails(user.getUsername(), user.getPassword(), user.isActive(), authorities,user.getOrganization());
    }
}
