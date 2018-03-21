package ch.tbmelabs.tv.core.authorizationserver.configuration;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import ch.tbmelabs.tv.core.authorizationserver.security.filter.OAuth2BearerTokenAuthenticationFilter;
import ch.tbmelabs.tv.core.authorizationserver.security.logging.AuthenticationFailureHandler;
import ch.tbmelabs.tv.core.authorizationserver.security.logging.AuthenticationSuccessHandler;
import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  private static final Logger LOGGER = LogManager.getLogger(SecurityConfiguration.class);

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private Environment environment;

  @Autowired
  private AuthenticationFailureHandler authenticationFailureHandler;

  @Autowired
  private AuthenticationSuccessHandler authenticationSuccessHandler;

  @Autowired
  private OAuth2BearerTokenAuthenticationFilter oAuth2AuthenticationFilter;

  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return authenticationManager;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    if (Arrays.asList(environment.getActiveProfiles()).contains(SpringApplicationProfile.DEV)) {
      LOGGER.warn("Profile \"" + SpringApplicationProfile.DEV + "\" is active: Web request debugging is enabled!");

      web.debug(true);
    }
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    String loginEndpoint = "/signin";

    // @formatter:off
    http
      
      .csrf().disable()
            
      .authorizeRequests()
        .antMatchers("/favicon.ico").permitAll()
        .antMatchers("/me","/user").permitAll()
        .antMatchers(loginEndpoint, "/signup/**").permitAll()
        .antMatchers("/public/**", "/vendor/**").permitAll()
      .anyRequest().authenticated()
      
      .and().formLogin()
        .loginPage(loginEndpoint)
        .loginProcessingUrl(loginEndpoint)
        .failureHandler(authenticationFailureHandler)
        .successHandler(authenticationSuccessHandler)
        .permitAll()
      .and().httpBasic()
      
      .and().logout()
        .logoutSuccessUrl(loginEndpoint + "?goodbye")
        .permitAll()
      
      .and()
        .addFilterBefore(oAuth2AuthenticationFilter, BasicAuthenticationFilter.class);
    // @formatter:on
  }
}