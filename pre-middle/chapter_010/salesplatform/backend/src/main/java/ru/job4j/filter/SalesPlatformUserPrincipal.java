package ru.job4j.filter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.job4j.model.Role;
import ru.job4j.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * User Principal.
 */
public class SalesPlatformUserPrincipal implements UserDetails {

    private final User user;
    private final Role role;

    public SalesPlatformUserPrincipal(final User user, final Role role) {
        this.user = user;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (null == role) {
            return Collections.emptySet();
        }
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.getType()));
        return authorities;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassw();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
}
