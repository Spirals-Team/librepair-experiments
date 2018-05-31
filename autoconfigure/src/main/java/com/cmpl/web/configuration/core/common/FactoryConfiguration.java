package com.cmpl.web.configuration.core.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.DisplayFactory;
import com.cmpl.web.core.factory.DisplayFactoryImpl;
import com.cmpl.web.core.factory.HtmlWidgetProvider;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.WidgetPageService;
import com.cmpl.web.core.widget.WidgetService;

/**
 * Configuration des factory
 * 
 * @author Louis
 *
 */
@Configuration
@EnablePluginRegistries({WidgetProviderPlugin.class})
public class FactoryConfiguration {

  @Autowired
  @Qualifier(value = "widgetProviders")
  private PluginRegistry<WidgetProviderPlugin, String> widgetProviders;

  @Bean
  DisplayFactory displayFactory(WebMessageSource messageSource, PageService pageService,
      NewsEntryService newsEntryService, WidgetPageService widgetPageService, WidgetService widgetService) {
    return new DisplayFactoryImpl(messageSource, pageService, newsEntryService, widgetPageService, widgetService,
        widgetProviders);
  }

  @Bean
  HtmlWidgetProvider htmlWidgetProvider() {
    return new HtmlWidgetProvider();
  }

}
