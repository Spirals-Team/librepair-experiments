package com.cmpl.web.core.design;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.models.Design;

@Configuration
@EntityScan(basePackageClasses = Design.class)
@EnableJpaRepositories(basePackageClasses = DesignRepository.class)
public class DesignConfiguration {

  @Bean
  public DesignDAO designDAO(DesignRepository designRepository, ApplicationEventPublisher publisher) {
    return new DesignDAOImpl(designRepository, publisher);
  }

  @Bean
  public DesignMapper designMapper() {
    return new DesignMapper();
  }

  @Bean
  public DesignService designService(DesignDAO designDAO, DesignMapper designMapper) {
    return new DesignServiceImpl(designDAO, designMapper);
  }

  @Bean
  public DesignTranslator designTranslator() {
    return new DesignTranslatorImpl();
  }

  @Bean
  public DesignDispatcher designDispatcher(DesignService designService, DesignTranslator designTranslator) {
    return new DesignDispatcherImpl(designService, designTranslator);
  }

}
