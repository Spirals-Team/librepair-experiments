package com.cmpl.web.configuration.manager.display;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;

import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.design.DesignService;
import com.cmpl.web.core.factory.DisplayFactory;
import com.cmpl.web.core.factory.DisplayFactoryImpl;
import com.cmpl.web.core.factory.HtmlWidgetProvider;
import com.cmpl.web.core.factory.carousel.CarouselWidgetProvider;
import com.cmpl.web.core.factory.media.ImageWidgetProvider;
import com.cmpl.web.core.factory.media.VideoWidgetProvider;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.factory.menu.MenuWidgetProvider;
import com.cmpl.web.core.factory.news.BlogEntryWidgetProvider;
import com.cmpl.web.core.factory.news.BlogWidgetProvider;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.news.entry.NewsEntryService;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.style.StyleService;
import com.cmpl.web.core.website.WebsiteService;
import com.cmpl.web.core.widget.WidgetService;
import com.cmpl.web.core.widget.page.WidgetPageService;

@Configuration
@EnablePluginRegistries({WidgetProviderPlugin.class})
public class WidgetProviderConfiguration {

  @Bean
  public BlogWidgetProvider blogWidgetProvider(WebMessageSource messageSource, ContextHolder contextHolder,
      NewsEntryService newsEntryService) {
    return new BlogWidgetProvider(messageSource, contextHolder, newsEntryService);

  }

  @Bean
  public BlogEntryWidgetProvider blogEntryWidgetProvider(NewsEntryService newsEntryService) {
    return new BlogEntryWidgetProvider(newsEntryService);
  }

  @Bean
  public ImageWidgetProvider imageWidgetProvider(MediaService mediaService) {
    return new ImageWidgetProvider(mediaService);
  }

  @Bean
  public VideoWidgetProvider videoWidgetProvider(MediaService mediaService) {
    return new VideoWidgetProvider(mediaService);
  }

  @Bean
  public MenuWidgetProvider menuWidgetProvider(MenuFactory menuFactory, PageService pageService) {
    return new MenuWidgetProvider(menuFactory, pageService);
  }

  @Bean
  public CarouselWidgetProvider carouselWidgetProvider(CarouselService carouselService) {
    return new CarouselWidgetProvider(carouselService);
  }

  @Autowired
  @Qualifier(value = "widgetProviders")
  private PluginRegistry<WidgetProviderPlugin, String> widgetProviders;

  @Bean
  public DisplayFactory displayFactory(WebMessageSource messageSource, PageService pageService,
      NewsEntryService newsEntryService, WidgetPageService widgetPageService, WidgetService widgetService,
      WebsiteService websiteService, SitemapService sitemapService, DesignService designService,
      StyleService styleService) {
    return new DisplayFactoryImpl(messageSource, pageService, newsEntryService, widgetPageService, widgetService,
        widgetProviders, websiteService, sitemapService, designService, styleService);
  }

  @Bean
  public HtmlWidgetProvider htmlWidgetProvider() {
    return new HtmlWidgetProvider();
  }
}
