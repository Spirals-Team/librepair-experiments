package ch.tbmelabs.tv.core.authorizationserver.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.PreAuthenticatedAuthenticationProviderImpl;
import ch.tbmelabs.tv.core.authorizationserver.service.userdetails.UserDetailsServiceImpl;

@Configuration
public class AuthenticationManagerConfiguration {
  @Autowired
  private ObjectPostProcessor<Object> objectPostProcessor;

  @Autowired
  private PreAuthenticatedAuthenticationProviderImpl preAuthenticationProvider;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(objectPostProcessor);
    builder.authenticationProvider(preAuthenticationProvider).userDetailsService(userDetailsService)
        .passwordEncoder(User.PASSWORD_ENCODER);
    return builder.build();
  }
}