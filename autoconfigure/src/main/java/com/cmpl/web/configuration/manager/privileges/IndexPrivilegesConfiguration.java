package com.cmpl.web.configuration.manager.privileges;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;

@Configuration
public class IndexPrivilegesConfiguration {

  @Bean
  public Privilege indexReadPrivilege() {
    return new SimplePrivilege("index", "index", "read");
  }

  @Bean
  public Privilege administrationReadPrivilege() {
    return new SimplePrivilege("administration", "administration", "read");
  }

  @Bean
  public Privilege webmasteringReadPrivilege() {
    return new SimplePrivilege("webmastering", "webmastering", "read");
  }

}
