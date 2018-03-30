package com.codesquad.library.security;

import com.codesquad.library.domain.authentication.ActiveUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CustomLoginToken extends UsernamePasswordAuthenticationToken{

    private ActiveUser activeUser;

    public CustomLoginToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public CustomLoginToken(ActiveUser activeUser) {
        super(activeUser, activeUser.getPassword(), activeUser.getAuthorities());
        this.activeUser = activeUser;
    }
}
