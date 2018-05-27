package ru.job4j.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.job4j.models.User;
import ru.job4j.storage.CarStor;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class CarUserDetailsService implements UserDetailsService {
    private final CarStor carStor = CarStor.INSTANCE;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = null;
        for (User entity: carStor.getuStor().getAll()) {
            if (login.equals(entity.getLogin())) {
                user = entity;
                break;
            }
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
        UserDetails details = new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                grantedAuthorities);
        return details;
    }
}
