package ch.tbmelabs.tv.core.adminserver.configuration;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ch.tbmelabs.tv.shared.constants.security.SecurityRole;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;
import de.codecentric.boot.admin.config.EnableAdminServer;

@Configuration
@EnableOAuth2Sso
@EnableAdminServer
public class OAuth2SSOAdminServerConfiguration extends WebSecurityConfigurerAdapter {
  private static final Logger LOGGER = LogManager.getLogger(OAuth2SSOAdminServerConfiguration.class);

  @Autowired
  private Environment environment;

  @Override
  public void configure(WebSecurity web) throws Exception {
    if (Arrays.asList(environment.getActiveProfiles()).contains(SpringApplicationProfile.DEV)) {
      LOGGER.warn("Profile \"" + SpringApplicationProfile.DEV + "\" is active: Web request debugging is enabled!");

      web.debug(true);
    }
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      
      .csrf().disable()
      
      .authorizeRequests()
        .antMatchers("/favicon.ico").permitAll()
        .antMatchers("/public/**", "/vendor/**").permitAll()
        .anyRequest().hasAnyRole(SecurityRole.GANDALF, SecurityRole.SERVER_ADMIN, SecurityRole.SERVER_SUPPORT)
      
      .and().exceptionHandling()
        .accessDeniedPage("/403.html");
    // @formatter:on
  }
}