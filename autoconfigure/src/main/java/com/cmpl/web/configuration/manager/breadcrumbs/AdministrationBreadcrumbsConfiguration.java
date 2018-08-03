package com.cmpl.web.configuration.manager.breadcrumbs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({GroupBreadcrumbsConfiguration.class, UserBreadcrumbsConfiguration.class, RoleBreadcrumbsConfiguration.class})
public class AdministrationBreadcrumbsConfiguration {

}
