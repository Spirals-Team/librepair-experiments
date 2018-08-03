package com.cmpl.web.configuration.core.common;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import com.cmpl.web.core.common.context.ContextHolder;

@Configuration
public class TemplateResolverConfiguration {

  @Autowired
  SpringTemplateEngine templateEngine;

  @Autowired
  ContextHolder contextHolder;

  @PostConstruct
  public void extension() {
    FileTemplateResolver resolver = new FileTemplateResolver();
    resolver.setPrefix(contextHolder.getTemplateBasePath());
    resolver.setSuffix(".html");
    resolver.setTemplateMode("HTML");
    resolver.setOrder(templateEngine.getTemplateResolvers().size());
    resolver.setCacheable(false);
    resolver.setCheckExistence(true);
    templateEngine.addTemplateResolver(resolver);
  }

}
