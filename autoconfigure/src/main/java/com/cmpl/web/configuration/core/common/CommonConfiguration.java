package com.cmpl.web.configuration.core.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({BackErrorViewResolverConfiguration.class, ContextConfiguration.class, EventsListenerConfiguration.class,
    LocaleConfiguration.class, MailConfiguration.class, ResourceConfiguration.class,
    TemplateResolverConfiguration.class, WebSecurityConfiguration.class})
public class CommonConfiguration {

}
