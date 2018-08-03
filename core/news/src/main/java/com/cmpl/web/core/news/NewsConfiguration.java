package com.cmpl.web.core.news;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.models.NewsContent;
import com.cmpl.web.core.models.NewsEntry;
import com.cmpl.web.core.models.NewsImage;
import com.cmpl.web.core.news.content.NewsContentDAO;
import com.cmpl.web.core.news.content.NewsContentDAOImpl;
import com.cmpl.web.core.news.content.NewsContentMapper;
import com.cmpl.web.core.news.content.NewsContentRepository;
import com.cmpl.web.core.news.content.NewsContentService;
import com.cmpl.web.core.news.content.NewsContentServiceImpl;
import com.cmpl.web.core.news.entry.NewsEntryDAO;
import com.cmpl.web.core.news.entry.NewsEntryDAOImpl;
import com.cmpl.web.core.news.entry.NewsEntryDispatcher;
import com.cmpl.web.core.news.entry.NewsEntryDispatcherImpl;
import com.cmpl.web.core.news.entry.NewsEntryMapper;
import com.cmpl.web.core.news.entry.NewsEntryRepository;
import com.cmpl.web.core.news.entry.NewsEntryService;
import com.cmpl.web.core.news.entry.NewsEntryServiceImpl;
import com.cmpl.web.core.news.entry.NewsEntryTranslator;
import com.cmpl.web.core.news.entry.NewsEntryTranslatorImpl;
import com.cmpl.web.core.news.image.NewsImageDAO;
import com.cmpl.web.core.news.image.NewsImageDAOImpl;
import com.cmpl.web.core.news.image.NewsImageMapper;
import com.cmpl.web.core.news.image.NewsImageRepository;
import com.cmpl.web.core.news.image.NewsImageService;
import com.cmpl.web.core.news.image.NewsImageServiceImpl;

@Configuration
@EntityScan(basePackageClasses = {NewsEntry.class, NewsContent.class, NewsImage.class})
@EnableJpaRepositories(basePackageClasses = {NewsEntryRepository.class, NewsImageRepository.class,
    NewsContentRepository.class})
public class NewsConfiguration {

  @Bean
  public NewsEntryDispatcher newsEntryDispatcher(NewsEntryTranslator translator, NewsEntryService newsEntryService,
      FileService fileService, MediaService mediaService) {
    return new NewsEntryDispatcherImpl(translator, newsEntryService, fileService, mediaService);
  }

  @Bean
  public NewsEntryTranslator newsEntryTranslator() {
    return new NewsEntryTranslatorImpl();
  }

  @Bean
  public NewsEntryDAO newsEntryDAO(NewsEntryRepository newsEntryRepository, ApplicationEventPublisher publisher) {
    return new NewsEntryDAOImpl(newsEntryRepository, publisher);
  }

  @Bean
  public NewsEntryMapper newsEntryMapper(NewsContentService newsContentService, NewsImageService newsImageService) {
    return new NewsEntryMapper(newsContentService, newsImageService);
  }

  @Bean
  public NewsEntryService newsEntryService(NewsEntryDAO newsEntryDAO, NewsEntryMapper newsEntryMapper,
      NewsImageService newsImageService, NewsContentService newsContentService) {
    return new NewsEntryServiceImpl(newsEntryDAO, newsImageService, newsContentService, newsEntryMapper);
  }

  @Bean
  public NewsImageDAO newsImageDAO(ApplicationEventPublisher publisher, NewsImageRepository newsImageRepository) {
    return new NewsImageDAOImpl(newsImageRepository, publisher);
  }

  @Bean
  public NewsImageMapper newsImageMapper(MediaService mediaService) {
    return new NewsImageMapper(mediaService);
  }

  @Bean
  public NewsImageService newsImageService(NewsImageDAO newsImageDAO, NewsImageMapper newsImageMapper) {
    return new NewsImageServiceImpl(newsImageDAO, newsImageMapper);
  }

  @Bean
  public NewsContentDAO newsContentDAO(ApplicationEventPublisher publisher,
      NewsContentRepository newsContentRepository) {
    return new NewsContentDAOImpl(newsContentRepository, publisher);
  }

  @Bean
  public NewsContentMapper newsContentMapper() {
    return new NewsContentMapper();
  }

  @Bean
  public NewsContentService newsContentService(NewsContentDAO newsContentDAO, NewsContentMapper newsContentMapper) {
    return new NewsContentServiceImpl(newsContentDAO, newsContentMapper);
  }

}
