package com.cmpl.web.configuration.core.news;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;

@Configuration
public class NewsPrivilegeConfiguration {

  @Bean
  public Privilege newsReadPrivilege() {
    return new SimplePrivilege("webmastering", "news", "read");
  }

  @Bean
  public Privilege newsWritePrivilege() {
    return new SimplePrivilege("webmastering", "news", "write");
  }

  @Bean
  public Privilege newsCreatePrivilege() {
    return new SimplePrivilege("webmastering", "news", "create");
  }

  @Bean
  public Privilege newsDeletePrivilege() {
    return new SimplePrivilege("webmastering", "news", "delete");
  }

}
