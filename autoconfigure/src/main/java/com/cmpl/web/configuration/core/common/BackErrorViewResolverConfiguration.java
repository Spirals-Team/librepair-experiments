package com.cmpl.web.configuration.core.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.error.BackErrorViewResolver;

@Configuration
public class BackErrorViewResolverConfiguration { 

  @Bean
  public BackErrorViewResolver backErrorViewResolver() {
    return new BackErrorViewResolver();
  }

}
