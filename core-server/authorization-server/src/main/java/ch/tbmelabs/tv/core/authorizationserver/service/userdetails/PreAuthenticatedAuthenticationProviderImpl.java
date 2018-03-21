package ch.tbmelabs.tv.core.authorizationserver.service.userdetails;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class PreAuthenticatedAuthenticationProviderImpl extends PreAuthenticatedAuthenticationProvider {
  private static final Logger LOGGER = LogManager.getLogger(PreAuthenticatedAuthenticationProviderImpl.class);

  @Autowired
  private PreAuthenticationUserDetailsServiceImpl userDetailsService;

  @PostConstruct
  public void initBean() {
    LOGGER.info("Initializing..");

    super.setPreAuthenticatedUserDetailsService(userDetailsService);
  }
}