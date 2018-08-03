package com.cmpl.web.configuration.manager.privileges;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CarouselPrivilegeConfiguration.class, MediaPrivilegeConfiguration.class, MenuPrivilegeConfiguration.class,
    NewsPrivilegeConfiguration.class, PagePrivilegeConfiguration.class, StylePrivilegeConfiguration.class,
    WidgetPrivilegeConfiguration.class, WebsitePrivilegeConfiguration.class, DesignPrivilegeConfiguration.class,
    SitemapPrivilegeConfiguration.class})
public class WebmasteringPrivilegesConfiguration {

}
