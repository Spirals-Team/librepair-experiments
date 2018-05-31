package com.cmpl.web.configuration.core.sitemap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.menu.MenuService;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.sitemap.SitemapServiceImpl;

@Configuration
public class SitemapConfiguration {

  @Bean
  SitemapService sitemapService(MenuService menuService, WebMessageSource messageSource, ContextHolder contextHolder) {
    return new SitemapServiceImpl(messageSource, menuService, contextHolder);
  }
}
