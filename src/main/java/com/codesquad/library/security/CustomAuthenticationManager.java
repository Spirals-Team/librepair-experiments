package com.codesquad.library.security;

import com.codesquad.library.domain.exceptions.security.BaseSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private CustomAuthenticationProvider provider;

    @Value("${oauth.resource.id}")
    private Set<String> resourceId;

    @Value("${oauth.client.id}")
    private String clientId;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException, BaseSecurityException {

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
        CustomLoginToken loggedInToken = (CustomLoginToken)provider.authenticate(authentication);

        return new OAuth2Authentication(generateOAuthRequest(), loggedInToken);
    }

    private OAuth2Request generateOAuthRequest() {
        return new OAuth2Request(null, clientId, null, true, null, resourceId, null, null, null);
    }
}
