package com.cmpl.web.configuration.core.common;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cmpl.web.core.common.user.ActionTokenService;
import com.cmpl.web.core.common.user.ActionTokenServiceImpl;
import com.cmpl.web.core.common.user.StatelessSecretTokenService;
import com.cmpl.web.core.user.UserService;
import com.cmpl.web.manager.ui.core.security.AuthenticationFailureListener;
import com.cmpl.web.manager.ui.core.security.AuthenticationSuccessListener;
import com.cmpl.web.manager.ui.core.security.LoginAttemptsService;
import com.cmpl.web.manager.ui.core.security.LoginAttemptsServiceImpl;
import com.cmpl.web.manager.ui.core.security.LoginAuthenticationProvider;
import com.cmpl.web.manager.ui.core.security.PasswordTooOldInterceptor;
import com.cmpl.web.manager.ui.core.user.LastConnectionUpdateAuthenticationSuccessHandlerImpl;

/**
 * Configuration de la securite
 * 
 * @author Louis
 *
 */
@Configuration
@EnableWebSecurity
@PropertySource("classpath:/core/core.properties")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfiguration {

  @Value("${backUserFile}")
  String backUserJson;

  @Value("${secret}")
  String secret;

  @Bean
  @ConditionalOnMissingBean(TokenService.class)
  public TokenService tokenService() {
    return new StatelessSecretTokenService(secret);
  }

  @Bean
  LoginAttemptsService loginAttemptsService() {
    return new LoginAttemptsServiceImpl(10);
  }

  @Bean
  ActionTokenService actionTokenService(TokenService tokenService) {
    return new ActionTokenServiceImpl(tokenService);
  }

  @Bean
  public AuthenticationFailureListener authenticationFailureListener(LoginAttemptsService loginAttemptService) {
    return new AuthenticationFailureListener(loginAttemptService);
  }

  @Bean
  PasswordTooOldInterceptor passwordTooOldInterceptor(UserService userService) {
    return new PasswordTooOldInterceptor(userService);
  }

  @Bean
  public AuthenticationSuccessListener authenticationSuccessListener(LoginAttemptsService loginAttemptService) {
    return new AuthenticationSuccessListener(loginAttemptService);
  }

  @Bean
  public LoginAuthenticationProvider loginAuthenticationProvider(UserDetailsService dbUserDetailsService,
      PasswordEncoder passwordEncoder, LoginAttemptsService userLoginAttemptsService) {

    LoginAuthenticationProvider provider = new LoginAuthenticationProvider(dbUserDetailsService,
        userLoginAttemptsService);
    provider.setPasswordEncoder(passwordEncoder);
    return provider;
  }

  @Bean
  @ConditionalOnMissingBean(PasswordEncoder.class)
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10, secureRandom());
  }

  @Bean
  @ConditionalOnMissingBean(SecureRandom.class)
  public SecureRandom secureRandom() {
    try {
      return SecureRandom.getInstance("SHA1PRNG");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Can't find the SHA1PRNG algorithm for generating random numbers", e);
    }
  }

  @Configuration
  public static class LoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    public final LoginAuthenticationProvider loginAuthenticationProvider;
    private final LastConnectionUpdateAuthenticationSuccessHandlerImpl lastConnectionUpdateAuthenticationSuccessHandler;

    public LoginWebSecurityConfigurerAdapter(LoginAuthenticationProvider loginAuthenticationProvider,
        LastConnectionUpdateAuthenticationSuccessHandlerImpl lastConnectionUpdateAuthenticationSuccessHandler) {
      this.loginAuthenticationProvider = loginAuthenticationProvider;
      this.lastConnectionUpdateAuthenticationSuccessHandler = lastConnectionUpdateAuthenticationSuccessHandler;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      String[] authorizedUrls = prepareAuthorizedUrls();
      http.headers().frameOptions().sameOrigin().and().authorizeRequests().antMatchers(authorizedUrls).permitAll()
          .anyRequest().authenticated().and().formLogin().loginPage("/login")
          .successHandler(lastConnectionUpdateAuthenticationSuccessHandler).permitAll().and().logout()
          .logoutRequestMatcher(new AntPathRequestMatcher("/manager/logout")).permitAll();

    }

    String[] prepareAuthorizedUrls() {
      return new String[]{"/", "/pages/**", "/manager-websocket/**", "/robots", "/robot", "/robot.txt", "/robots.txt",
          "/webjars/**", "/js/**", "/img/**", "/css/**", "/**/favicon.ico", "/sitemap.xml", "/public/**", "/blog/**",
          "/widgets/**", "/forgotten_password", "/change_password"};
    }

    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
      return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(loginAuthenticationProvider);
    }

  }

  @Configuration
  public class InterceptorsConfiguration implements WebMvcConfigurer {

    public final PasswordTooOldInterceptor passwordTooOldInterceptor;

    public InterceptorsConfiguration(PasswordTooOldInterceptor passwordTooOldInterceptor) {
      this.passwordTooOldInterceptor = passwordTooOldInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

      registry.addInterceptor(passwordTooOldInterceptor).addPathPatterns("/manager/**");

    }
  }

}
