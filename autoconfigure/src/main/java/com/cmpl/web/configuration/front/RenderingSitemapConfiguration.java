package com.cmpl.web.configuration.front;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.menu.MenuService;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.sitemap.rendering.RenderingSitemapService;
import com.cmpl.web.core.sitemap.rendering.RenderingSitemapServiceImpl;
import com.cmpl.web.core.website.WebsiteService;

@Configuration
public class RenderingSitemapConfiguration {

  @Bean
  public RenderingSitemapService renderingSitemapService(MenuService menuService, WebMessageSource messageSource,
      WebsiteService websiteService, SitemapService sitemapService) {
    return new RenderingSitemapServiceImpl(messageSource, menuService, websiteService, sitemapService);
  }

}
