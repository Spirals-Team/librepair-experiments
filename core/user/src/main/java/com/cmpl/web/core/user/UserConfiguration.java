package com.cmpl.web.core.user;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cmpl.web.core.common.mail.MailSender;
import com.cmpl.web.core.common.user.ActionTokenService;
import com.cmpl.web.core.models.User;

@Configuration
@EntityScan(basePackageClasses = User.class)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class UserConfiguration {

  @Bean
  public UserMapper userMapper() {
    return new UserMapper();
  }

  @Bean
  public UserDAO userDAO(UserRepository userRepository, ApplicationEventPublisher publisher) {
    return new UserDAOImpl(userRepository, publisher);
  }

  @Bean
  public UserService userService(UserMapper userMapper, UserDAO userDAO, ActionTokenService actionTokenService,
      UserMailService userMailService) {
    return new UserServiceImpl(actionTokenService, userMailService, userDAO, userMapper);
  }

  @Bean
  public UserMailService userMailService(MailSender mailSender) {
    return new UserMailServiceImpl(mailSender);
  }

  @Bean
  public UserTranslator userTranslator() {
    return new UserTranslatorImpl();
  }

  @Bean
  public UserDispatcher userDispatcher(UserTranslator userTranslator, UserService userService,
      PasswordEncoder passwordEncoder, ActionTokenService tokenService) {
    return new UserDispatcherImpl(userTranslator, userService, passwordEncoder, tokenService);
  }

}
