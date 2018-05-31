package com.cmpl.web.manager.ui.core.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class LoginAuthenticationProvider extends DaoAuthenticationProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuthenticationProvider.class);

  private final LoginAttemptsService userLoginAttemptsService;

  public LoginAuthenticationProvider(UserDetailsService userDetailsService,
      LoginAttemptsService userLoginAttemptsService) {
    super.setUserDetailsService(userDetailsService);
    this.userLoginAttemptsService = userLoginAttemptsService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    Object detailsObject = authentication.getDetails();
    if (detailsObject instanceof WebAuthenticationDetails) {
      final WebAuthenticationDetails details = (WebAuthenticationDetails) detailsObject;

      if (userLoginAttemptsService.isBlocked(authentication.getName(), details.getRemoteAddress())) {
        LOGGER.error("L'utilisateur de login " + authentication.getName() + " pour l'ip " + details.getRemoteAddress()
            + " est bloqué");
        throw new LockedException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked",
            "User account is locked"));
      }

      try {
        Authentication auth = super.authenticate(authentication);
        userLoginAttemptsService.successfulAttempt(authentication.getName(), details.getRemoteAddress());
        return auth;
      } catch (BadCredentialsException e) {
        userLoginAttemptsService.failAttempt(authentication.getName(), details.getRemoteAddress());
        throw e;
      }
    }
    return super.authenticate(authentication);
  }

}
