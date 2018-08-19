package ua.epam.spring.hometask.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;
import ua.epam.spring.hometask.util.exception.NotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by Vladimir_Vysokomorny on 15-Jan-18.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User u;
        try {
            u = userService.getUserByEmail(s);
        } catch (NotFoundException nfe) {
            throw new UsernameNotFoundException(s + " not found!");
        }

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return u.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toSet());
            }

            @Override
            public String getPassword() {
                return u.getPassword();
            }

            @Override
            public String getUsername() {
                return u.getEmail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
