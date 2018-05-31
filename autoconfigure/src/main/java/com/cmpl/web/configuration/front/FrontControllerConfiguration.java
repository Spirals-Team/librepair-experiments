package com.cmpl.web.configuration.front;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.factory.DisplayFactory;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.front.ui.blog.BlogController;
import com.cmpl.web.front.ui.index.IndexController;
import com.cmpl.web.front.ui.media.MediaController;
import com.cmpl.web.front.ui.page.AMPController;
import com.cmpl.web.front.ui.page.PageController;
import com.cmpl.web.front.ui.robot.RobotsController;
import com.cmpl.web.front.ui.sitemap.SitemapController;
import com.cmpl.web.front.ui.widgets.WidgetController;

@Configuration
public class FrontControllerConfiguration {

  @Bean
  public IndexController frontIndexController(DisplayFactory displayFactory) {
    return new IndexController(displayFactory);
  }

  @Bean
  public MediaController frontMediaController(MediaService mediaService) {
    return new MediaController(mediaService);
  }

  @Bean
  public PageController frontPageController(DisplayFactory displayFactory) {
    return new PageController(displayFactory);
  }

  @Bean
  public AMPController frontAMPController(DisplayFactory displayFactory) {
    return new AMPController(displayFactory);
  }

  @Bean
  public SitemapController sitemapController(SitemapService sitemapService) {
    return new SitemapController(sitemapService);
  }

  @Bean
  public BlogController blogController(DisplayFactory displayFactory) {
    return new BlogController(displayFactory);
  }

  @Bean
  public WidgetController widgetController(DisplayFactory displayFactory) {
    return new WidgetController(displayFactory);
  }

  @Bean
  public RobotsController robotsController(ContextHolder contextHolder) {
    return new RobotsController(contextHolder);
  }

}
