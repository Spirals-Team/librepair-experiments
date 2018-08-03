package com.cmpl.web.configuration.core.common;

import java.util.Locale;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.core.events_listeners.GroupEventsListener;
import com.cmpl.core.events_listeners.MediaEventsListeners;
import com.cmpl.core.events_listeners.NewsEventsListeners;
import com.cmpl.core.events_listeners.PageEventsListeners;
import com.cmpl.core.events_listeners.RoleEventsListeners;
import com.cmpl.core.events_listeners.StyleEventsListeners;
import com.cmpl.core.events_listeners.UserEventsListeners;
import com.cmpl.core.events_listeners.WebsiteEventsListeners;
import com.cmpl.core.events_listeners.WidgetEventsListeners;
import com.cmpl.web.core.design.DesignService;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.news.content.NewsContentService;
import com.cmpl.web.core.news.image.NewsImageService;
import com.cmpl.web.core.responsibility.ResponsibilityService;
import com.cmpl.web.core.role.privilege.PrivilegeService;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.widget.page.WidgetPageService;

@Configuration
public class EventsListenerConfiguration {

  @Bean
  public GroupEventsListener groupEventsListener(ResponsibilityService responsibilityService) {
    return new GroupEventsListener(responsibilityService);
  }

  @Bean
  public MediaEventsListeners mediaEventsListener(FileService fileService) {
    return new MediaEventsListeners(fileService);
  }

  @Bean
  public NewsEventsListeners newsEventsListener(NewsContentService newsContentService,
      NewsImageService newsImageService) {
    return new NewsEventsListeners(newsContentService, newsImageService);
  }

  @Bean
  public PageEventsListeners pageEventsListener(WidgetPageService widgetPageService, FileService fileService,
      Set<Locale> availableLocales, SitemapService sitemapService) {
    return new PageEventsListeners(widgetPageService, fileService, availableLocales, sitemapService);
  }

  @Bean
  public RoleEventsListeners roleEventsListener(ResponsibilityService responsibilityService,
      PrivilegeService privilegeService) {
    return new RoleEventsListeners(responsibilityService, privilegeService);
  }

  @Bean
  public UserEventsListeners userEventsListener(ResponsibilityService responsibilityService) {
    return new UserEventsListeners(responsibilityService);
  }

  @Bean
  public WidgetEventsListeners widgetEventsListener(WidgetPageService widgetPageService, FileService fileService,
      Set<Locale> availableLocales) {
    return new WidgetEventsListeners(widgetPageService, fileService, availableLocales);
  }

  @Bean
  public WebsiteEventsListeners websiteEventsListeners(DesignService designService, SitemapService sitemapService) {
    return new WebsiteEventsListeners(designService, sitemapService);
  }

  @Bean
  public StyleEventsListeners styleEventsListeners(DesignService designService) {
    return new StyleEventsListeners(designService);
  }
}
