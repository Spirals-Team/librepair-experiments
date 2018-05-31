package com.cmpl.web.configuration.core.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;

@Configuration
public class UserPrivilegeConfiguration {

  @Bean
  public Privilege usersReadPrivilege() {
    return new SimplePrivilege("administration", "users", "read");
  }

  @Bean
  public Privilege usersWritePrivilege() {
    return new SimplePrivilege("administration", "users", "write");
  }

  @Bean
  public Privilege usersCreatePrivilege() {
    return new SimplePrivilege("administration", "users", "create");
  }

  @Bean
  public Privilege usersDeletePrivilege() {
    return new SimplePrivilege("administration", "users", "delete");
  }
}
