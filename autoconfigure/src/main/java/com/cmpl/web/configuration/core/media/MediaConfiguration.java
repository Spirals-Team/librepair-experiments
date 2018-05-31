package com.cmpl.web.configuration.core.media;

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

import com.cmpl.core.events_listeners.MediaEventsListeners;
import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.factory.media.ImageWidgetProvider;
import com.cmpl.web.core.factory.media.MediaManagerDisplayFactory;
import com.cmpl.web.core.factory.media.MediaManagerDisplayFactoryImpl;
import com.cmpl.web.core.factory.media.VideoWidgetProvider;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.Media;
import com.cmpl.web.core.media.MediaRepository;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.media.MediaServiceImpl;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.page.BACK_PAGE;

@Configuration
@EntityScan(basePackageClasses = Media.class)
@EnableJpaRepositories(basePackageClasses = MediaRepository.class)
public class MediaConfiguration {

  @Bean
  public MediaService mediaService(ApplicationEventPublisher publisher, MediaRepository mediaRepository,
      FileService fileService) {
    return new MediaServiceImpl(publisher, mediaRepository, fileService);
  }

  @Bean
  BackMenuItem mediasBackMenuItem(BackMenuItem webmastering, Privilege mediaReadPrivilege) {
    return BackMenuItemBuilder.create().href("back.medias.href").label("back.medias.label").title("back.medias.title")
        .order(4).iconClass("fa fa-file-image-o").parent(webmastering).privilege(mediaReadPrivilege.privilege())
        .build();
  }

  @Bean
  BreadCrumb mediaBreadCrumb() {
    return BreadCrumbBuilder.create().items(mediaBreadCrumbItems()).page(BACK_PAGE.MEDIA_VIEW).build();
  }

  @Bean
  BreadCrumb mediaUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(mediaBreadCrumbItems()).page(BACK_PAGE.MEDIA_UPLOAD).build();
  }

  @Bean
  BreadCrumb mediaVisualizeBreadCrumb() {
    return BreadCrumbBuilder.create().items(mediaVisualizeBreadCrumbItems()).page(BACK_PAGE.MEDIA_VISUALIZE).build();
  }

  @Bean
  ImageWidgetProvider imageWidgetProvider(MediaService mediaService) {
    return new ImageWidgetProvider(mediaService);
  }

  @Bean
  VideoWidgetProvider videoWidgetProvider(MediaService mediaService) {
    return new VideoWidgetProvider(mediaService);
  }

  List<BreadCrumbItem> mediaBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.medias.title").href("back.medias.href").build());
    return items;
  }

  List<BreadCrumbItem> mediaVisualizeBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.title").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.medias.title").href("back.medias.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.medias.visualize.label").href("#").build());
    return items;
  }

  @Bean
  public MediaManagerDisplayFactory mediaManagerDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      MediaService mediaService, ContextHolder contextHolder, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs,
      Set<Locale> availableLocales) {
    return new MediaManagerDisplayFactoryImpl(menuFactory, messageSource, mediaService, contextHolder, breadCrumbs,
        availableLocales);
  }

  @Bean
  MediaEventsListeners mediaEventsListener(FileService fileService) {
    return new MediaEventsListeners(fileService);
  }

}
