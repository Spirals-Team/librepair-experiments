package com.cmpl.web.configuration.manager.breadcrumbs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WidgetBreadcrumbsConfiguration.class, MediaBreadcrumbsConfiguration.class, MenuBreadcrumbsConfiguration.class,
    CarouselBreadcrumbsConfiguration.class, NewsBreadcrumbsConfiguration.class, PageBreadcrumbsConfiguration.class,
    StyleBreadcrumbsConfiguration.class, WebsiteBreadcrumbsConfiguration.class})
public class WebmasteringBreadcrumbsConfiguration {

}
