package com.cmpl.web.configuration.manager.privileges;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;

@Configuration
public class GroupPrivilegeConfiguration {

  @Bean
  public Privilege groupsReadPrivilege() {
    return new SimplePrivilege("administration", "groups", "read");
  }

  @Bean
  public Privilege groupsWritePrivilege() {
    return new SimplePrivilege("administration", "groups", "write");
  }

  @Bean
  public Privilege groupsCreatePrivilege() {
    return new SimplePrivilege("administration", "groups", "create");
  }

  @Bean
  public Privilege groupsDeletePrivilege() {
    return new SimplePrivilege("administration", "groups", "delete");
  }

}
