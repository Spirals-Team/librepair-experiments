package ru.curriculum.application.auth.principal;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.curriculum.domain.admin.user.entity.User;

import java.util.ArrayList;
import java.util.Collection;

public class UserPrincipal implements UserDetails {
    private AuthorityFactory authorityFactory;
    private User user;

    public UserPrincipal(User user) {
        this.user = user;
        this.authorityFactory = new AuthorityFactory();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList();
        authorities.add(authorityFactory.create(user.role()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.password().hash();
    }

    @Override
    public String getUsername() {
        return user.username();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //TODO: в каких случая блочить пользователя
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
