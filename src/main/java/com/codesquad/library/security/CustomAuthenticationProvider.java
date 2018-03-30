package com.codesquad.library.security;

import com.codesquad.library.domain.authentication.ActiveUser;
import com.codesquad.library.domain.exceptions.security.BadCredentialsException;
import com.codesquad.library.domain.exceptions.security.BaseSecurityException;
import com.codesquad.library.domain.exceptions.security.NoAccountPresentException;
import com.codesquad.library.dtos.security.FormLoginDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private ActiveUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException, BaseSecurityException {

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;

        FormLoginDocument document = (FormLoginDocument)token.getPrincipal();
        ActiveUser attemptedUser = getUserInfo(document);

        if(isValidCredentials(document, attemptedUser)) {
            return new CustomLoginToken(attemptedUser);
        }

        throw new BadCredentialsException();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }

    private ActiveUser getUserInfo(FormLoginDocument document) throws NoAccountPresentException {
        return (ActiveUser)userDetailsService.loadUserByUsername(document.getLoginEmail());
    }

    private boolean isValidCredentials(FormLoginDocument document, ActiveUser attemptedUser){
        return passwordEncoder.matches(document.getPassword(), attemptedUser.getPassword());
    }

}
