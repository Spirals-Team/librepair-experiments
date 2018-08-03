package com.cmpl.web.core.media;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.models.Media;

@Configuration
@EntityScan(basePackageClasses = Media.class)
@EnableJpaRepositories(basePackageClasses = MediaRepository.class)
public class MediaConfiguration {

  @Bean
  public MediaDAO mediaDAO(MediaRepository mediaRepository, ApplicationEventPublisher publisher) {
    return new MediaDAOImpl(mediaRepository, publisher);
  }

  @Bean
  public MediaMapper mediaMapper() {
    return new MediaMapper();
  }

  @Bean
  public MediaService mediaService(MediaDAO mediaDAO, MediaMapper mediaMapper, FileService fileService) {
    return new MediaServiceImpl(mediaDAO, mediaMapper, fileService);
  }

}
