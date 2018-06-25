package com.hashmapinc.haf.providers;


import com.hashmapinc.haf.models.SecurityUser;
import com.hashmapinc.haf.models.UserInformation;
import com.hashmapinc.haf.services.DatabaseUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

@Component
@ConditionalOnProperty(value = "security.provider", havingValue = "oauth2-local")
public class DatabaseAuthenticationProvider extends CustomAuthenticationProvider{

    private final DatabaseUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public DatabaseAuthenticationProvider(final DatabaseUserDetailsService userDetailsService, final BCryptPasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        if (!(authentication instanceof UsernamePasswordAuthenticationToken) &&
                !(authentication instanceof PreAuthenticatedAuthenticationToken)) {
            throw new BadCredentialsException("Authentication Failed. Bad user principal.");
        }

        if(authentication instanceof UsernamePasswordAuthenticationToken) {
            String username = (String) authentication.getPrincipal();
            String password = (String) authentication.getCredentials();
            String clientId = null;
            if(authentication.getDetails() instanceof Map) {
                Map<String, String> details = (Map<String, String>) authentication.getDetails();
                clientId = details.get("client_id");
            }
            return authenticateByUsernameAndPassword(username, password, clientId);
        }else{
            SecurityUser user = (SecurityUser)((UsernamePasswordAuthenticationToken)authentication.getPrincipal()).getPrincipal();
            String username = user.getUser().getUserName();
            PreAuthenticatedAuthenticationToken auth  = (PreAuthenticatedAuthenticationToken)authentication;
            return reAuthenticateWithUsername(username, user.getUser().getClientId(), auth);
        }
    }

    protected Authentication authenticateByUsernameAndPassword(String username, String password, String clientId) {
        UserInformation userInfo = userDetailsService.loadUserByUsername(username, clientId);
        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        if (!userInfo.isEnabled()) {
            throw new DisabledException("User is not active");
        }

        if (!encoder.matches(password, userInfo.getPassword())) {
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }
        if (userInfo.getAuthorities() == null || userInfo.getAuthorities().isEmpty())
            throw new InsufficientAuthenticationException("User has no authority assigned");

        SecurityUser securityUser = new SecurityUser(userInfo, userInfo.isEnabled());

        return new UsernamePasswordAuthenticationToken(securityUser, password, securityUser.getAuthorities());
    }

    protected Authentication reAuthenticateWithUsername(String username, String clientId, PreAuthenticatedAuthenticationToken auth){
        UserInformation userInfo = userDetailsService.loadUserByUsername(username, clientId);
        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        if (!userInfo.isEnabled()) {
            throw new DisabledException("User is not active");
        }

        if (userInfo.getAuthorities() == null || userInfo.getAuthorities().isEmpty())
            throw new InsufficientAuthenticationException("User has no authority assigned");

        SecurityUser securityUser = new SecurityUser(userInfo, userInfo.isEnabled());

        PreAuthenticatedAuthenticationToken result = new PreAuthenticatedAuthenticationToken(securityUser, auth.getCredentials(), securityUser.getAuthorities());
        result.setDetails(auth.getDetails());

        return result;
    }
}
