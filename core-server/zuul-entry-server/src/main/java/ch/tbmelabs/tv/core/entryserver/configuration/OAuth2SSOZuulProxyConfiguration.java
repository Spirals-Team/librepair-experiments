package ch.tbmelabs.tv.core.entryserver.configuration;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Configuration
@EnableZuulProxy
@EnableOAuth2Sso
public class OAuth2SSOZuulProxyConfiguration extends WebSecurityConfigurerAdapter {
  private static final Logger LOGGER = LogManager.getLogger(OAuth2SSOZuulProxyConfiguration.class);

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
        .antMatchers("/", "/favicon.ico").permitAll()
        .antMatchers("/authenticated").permitAll()
        .antMatchers("/public/**", "/vendor/**").permitAll()
        .anyRequest().authenticated()
      
      .and().logout()
         .logoutSuccessUrl("/")
        .permitAll();
    // @formatter:on
  }
}