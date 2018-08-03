package com.cmpl.web.core.website;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.models.Website;

@Configuration
@EntityScan(basePackageClasses = {Website.class})
@EnableJpaRepositories(basePackageClasses = {WebsiteRepository.class})
public class WebsiteConfiguration {

  @Bean
  public WebsiteMapper websiteMapper() {
    return new WebsiteMapper();
  }

  @Bean
  public WebsiteDAO websiteDAO(ApplicationEventPublisher publisher, WebsiteRepository websiteRepository) {
    return new WebsiteDAOImpl(websiteRepository, publisher);
  }

  @Bean
  public WebsiteService websiteService(WebsiteDAO websiteDAO, WebsiteMapper websiteMapper) {
    return new WebsiteServiceImpl(websiteDAO, websiteMapper);
  }

  @Bean
  public WebsiteTranslator websiteTranslator() {
    return new WebsiteTranslatorImpl();
  }

  @Bean
  public WebsiteDispatcher websiteDispatcher(WebsiteService websiteService, WebsiteTranslator websiteTranslator) {
    return new WebsiteDispatcherImpl(websiteService, websiteTranslator);
  }
}
