package com.cmpl.web.configuration.manager.privileges;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;

@Configuration
public class SitemapPrivilegeConfiguration {

  @Bean
  public Privilege sitemapsReadPrivilege() {
    return new SimplePrivilege("webmastering", "sitemaps", "read");
  }

  @Bean
  public Privilege sitemapsWritePrivilege() {
    return new SimplePrivilege("webmastering", "sitemaps", "write");
  }

  @Bean
  public Privilege sitemapsCreatePrivilege() {
    return new SimplePrivilege("webmastering", "sitemaps", "create");
  }

  @Bean
  public Privilege sitemapsDeletePrivilege() {
    return new SimplePrivilege("webmastering", "sitemaps", "delete");
  }
}
