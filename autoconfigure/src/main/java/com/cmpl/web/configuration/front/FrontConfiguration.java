package com.cmpl.web.configuration.front;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({FrontControllerConfiguration.class, RenderingSitemapConfiguration.class})
public class FrontConfiguration {

}
