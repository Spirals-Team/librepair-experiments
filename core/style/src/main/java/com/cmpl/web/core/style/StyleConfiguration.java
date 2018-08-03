package com.cmpl.web.core.style;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.models.Style;

@Configuration
@EntityScan(basePackageClasses = Style.class)
@EnableJpaRepositories(basePackageClasses = StyleRepository.class)
public class StyleConfiguration {

  @Bean
  public StyleDAO styleDAO(StyleRepository styleRepository, ApplicationEventPublisher publisher) {
    return new StyleDAOImpl(styleRepository, publisher);
  }

  @Bean
  public StyleMapper styleMapper(MediaService mediaService, FileService fileService) {
    return new StyleMapper(mediaService, fileService);
  }

  @Bean
  public StyleService styleService(StyleDAO styleDAO, StyleMapper styleMapper, MediaService mediaService,
      FileService fileService) {
    return new StyleServiceImpl(styleDAO, styleMapper, mediaService, fileService);
  }

  @Bean
  public StyleDispatcher styleDispacther(StyleService styleService, StyleTranslator styleTranslator) {
    return new StyleDispatcherImpl(styleService, styleTranslator);
  }

  @Bean
  public StyleTranslator styleTranslator() {
    return new StyleTranslatorImpl();
  }

}
