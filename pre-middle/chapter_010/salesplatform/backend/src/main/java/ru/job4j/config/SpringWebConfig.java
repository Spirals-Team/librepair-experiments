package ru.job4j.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * Web MVC configuration adapter.
 */
@EnableWebMvc
@Configuration
@ComponentScan({"ru.job4j.controller", "ru.job4j.filter"})
public class SpringWebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**")
            .addResourceLocations("/").setCachePeriod(31556926)
            .addResourceLocations("classpath:/public/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        // no limit
        resolver.setMaxUploadSize(-1);
        return resolver;
    }

    @Bean
    public ViewResolver viewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewClass(InternalResourceView.class);
        return viewResolver;
    }
}
