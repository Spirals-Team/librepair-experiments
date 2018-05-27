package ru.iac.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Configuration
public class SpringWebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "main");
        registry.addViewController("/main").setViewName("main");
    }
}
