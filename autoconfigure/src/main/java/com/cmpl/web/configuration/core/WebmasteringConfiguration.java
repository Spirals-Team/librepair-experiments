package com.cmpl.web.configuration.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.cmpl.web.core.carousel.CarouselConfiguration;
import com.cmpl.web.core.design.DesignConfiguration;
import com.cmpl.web.core.media.MediaConfiguration;
import com.cmpl.web.core.menu.MenuConfiguration;
import com.cmpl.web.core.news.NewsConfiguration;
import com.cmpl.web.core.page.PageConfiguration;
import com.cmpl.web.core.sitemap.SitemapConfiguration;
import com.cmpl.web.core.style.StyleConfiguration;
import com.cmpl.web.core.website.WebsiteConfiguration;
import com.cmpl.web.core.widget.WidgetConfiguration;

@Configuration
@Import({StyleConfiguration.class, MenuConfiguration.class, PageConfiguration.class, WidgetConfiguration.class,
    CarouselConfiguration.class, NewsConfiguration.class, MediaConfiguration.class, WebsiteConfiguration.class,
    DesignConfiguration.class, SitemapConfiguration.class})
public class WebmasteringConfiguration {

}
