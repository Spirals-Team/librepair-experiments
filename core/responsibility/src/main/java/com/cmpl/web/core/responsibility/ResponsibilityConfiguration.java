package com.cmpl.web.core.responsibility;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.models.Responsibility;

@Configuration
@EntityScan(basePackageClasses = Responsibility.class)
@EnableJpaRepositories(basePackageClasses = ResponsibilityRepository.class)
public class ResponsibilityConfiguration {

  @Bean
  public ResponsibilityDAO responsibilityDAO(ResponsibilityRepository responsibilityRepository,
      ApplicationEventPublisher publisher) {
    return new ResponsibilityDAOImpl(responsibilityRepository, publisher);
  }

  @Bean
  public ResponsibilityMapper responsibilityMapper() {
    return new ResponsibilityMapper();
  }

  @Bean
  public ResponsibilityService responsibilityService(ResponsibilityDAO responsibilityDAO,
      ResponsibilityMapper responsibilityMapper) {
    return new ResponsibilityServiceImpl(responsibilityDAO, responsibilityMapper);
  }

  @Bean
  public ResponsibilityTranslator responsibilityTranslator() {
    return new ResponsibilityTranslatorImpl();
  }

  @Bean
  public ResponsibilityDispatcher responsibilityDispatcher(ResponsibilityService responsibilityService,
      ResponsibilityTranslator responsibilityTranslator) {
    return new ResponsibilityDispatcherImpl(responsibilityService, responsibilityTranslator);
  }

}
