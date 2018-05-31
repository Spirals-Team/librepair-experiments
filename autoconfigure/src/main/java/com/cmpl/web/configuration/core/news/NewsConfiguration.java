package com.cmpl.web.configuration.core.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.core.events_listeners.NewsEventsListeners;
import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.factory.news.BlogEntryWidgetProvider;
import com.cmpl.web.core.factory.news.BlogWidgetProvider;
import com.cmpl.web.core.factory.news.NewsManagerDisplayFactory;
import com.cmpl.web.core.factory.news.NewsManagerDisplayFactoryImpl;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.news.NewsContent;
import com.cmpl.web.core.news.NewsContentRepository;
import com.cmpl.web.core.news.NewsContentService;
import com.cmpl.web.core.news.NewsContentServiceImpl;
import com.cmpl.web.core.news.NewsEntry;
import com.cmpl.web.core.news.NewsEntryDispatcher;
import com.cmpl.web.core.news.NewsEntryDispatcherImpl;
import com.cmpl.web.core.news.NewsEntryRepository;
import com.cmpl.web.core.news.NewsEntryRequestValidator;
import com.cmpl.web.core.news.NewsEntryRequestValidatorImpl;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.news.NewsEntryServiceImpl;
import com.cmpl.web.core.news.NewsEntryTranslator;
import com.cmpl.web.core.news.NewsEntryTranslatorImpl;
import com.cmpl.web.core.news.NewsImage;
import com.cmpl.web.core.news.NewsImageRepository;
import com.cmpl.web.core.news.NewsImageService;
import com.cmpl.web.core.news.NewsImageServiceImpl;
import com.cmpl.web.core.page.BACK_PAGE;

@Configuration
@EntityScan(basePackageClasses = {NewsEntry.class, NewsContent.class, NewsImage.class})
@EnableJpaRepositories(basePackageClasses = {NewsEntryRepository.class, NewsImageRepository.class,
    NewsContentRepository.class})
public class NewsConfiguration {

  @Bean
  NewsEntryDispatcher newsEntryDispatcher(NewsEntryRequestValidator validator, NewsEntryTranslator translator,
      NewsEntryService newsEntryService, FileService fileService, MediaService mediaService) {
    return new NewsEntryDispatcherImpl(validator, translator, newsEntryService, fileService, mediaService);
  }

  @Bean
  BackMenuItem newsBackMenuItem(BackMenuItem webmastering, Privilege newsReadPrivilege) {
    return BackMenuItemBuilder.create().href("back.news.href").label("back.news.label").title("back.news.title")
        .order(6).iconClass("fa fa-newspaper-o").parent(webmastering).privilege(newsReadPrivilege.privilege()).build();
  }

  @Bean
  BreadCrumb newsViewBreadCrumb() {
    return BreadCrumbBuilder.create().items(newsBreadCrumbItems()).page(BACK_PAGE.NEWS_VIEW).build();
  }

  @Bean
  BreadCrumb newsEditBreadCrumb() {
    return BreadCrumbBuilder.create().items(newsUpdateBreadCrumbItems()).page(BACK_PAGE.NEWS_UPDATE).build();
  }

  @Bean
  BreadCrumb newsCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(newsBreadCrumbItems()).page(BACK_PAGE.NEWS_CREATE).build();
  }

  List<BreadCrumbItem> newsBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.news.title").href("back.news.href").build());
    return items;
  }

  List<BreadCrumbItem> newsUpdateBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.news.title").href("back.news.href").build());
    items.add(BreadCrumbItemBuilder.create().text("news.update.title").href("#").build());
    return items;
  }

  List<BreadCrumbItem> newsCreateBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("/manager/").build());
    items.add(BreadCrumbItemBuilder.create().text("back.news.title").href("back.news.href").build());
    items.add(BreadCrumbItemBuilder.create().text("news.create.title").href("#").build());
    return items;
  }

  @Bean
  NewsManagerDisplayFactory newsManagerDisplayFactory(ContextHolder contextHolder, MenuFactory menuFactory,
      WebMessageSource messageSource, NewsEntryService newsEntryService,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs, Set<Locale> availableLocales) {
    return new NewsManagerDisplayFactoryImpl(contextHolder, menuFactory, messageSource, newsEntryService, breadCrumbs,
        availableLocales);
  }

  @Bean
  NewsEntryTranslator newsEntryTranslator() {
    return new NewsEntryTranslatorImpl();
  }

  @Bean
  NewsEntryRequestValidator newsEntryRequestValidator(WebMessageSource messageSource) {
    return new NewsEntryRequestValidatorImpl(messageSource);
  }

  @Bean
  NewsEntryService newsEntryService(ApplicationEventPublisher publisher, NewsEntryRepository newsEntryRepository,
      NewsImageService newsImageService, NewsContentService newsContentService) {
    return new NewsEntryServiceImpl(publisher, newsEntryRepository, newsImageService, newsContentService);
  }

  @Bean
  NewsImageService newsImageService(ApplicationEventPublisher publisher, NewsImageRepository newsImageRepository,
      MediaService mediaService) {
    return new NewsImageServiceImpl(publisher, newsImageRepository, mediaService);
  }

  @Bean
  NewsContentService newsContentService(ApplicationEventPublisher publisher,
      NewsContentRepository newsContentRepository) {
    return new NewsContentServiceImpl(publisher, newsContentRepository);
  }

  @Bean
  BlogWidgetProvider blogWidgetProvider(WebMessageSource messageSource, ContextHolder contextHolder,
      NewsEntryService newsEntryService) {
    return new BlogWidgetProvider(messageSource, contextHolder, newsEntryService);

  }

  @Bean
  BlogEntryWidgetProvider blogEntryWidgetProvider(NewsEntryService newsEntryService) {
    return new BlogEntryWidgetProvider(newsEntryService);
  }

  @Bean
  NewsEventsListeners newsEventsListener(NewsContentService newsContentService, NewsImageService newsImageService,
      FileService fileService) {
    return new NewsEventsListeners(newsContentService, newsImageService, fileService);
  }
}
