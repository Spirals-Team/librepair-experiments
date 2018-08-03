package com.cmpl.web.configuration.manager.privileges;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;

@Configuration
public class MembershipPrivilegeConfiguration {

  @Bean
  public Privilege membershipsReadPrivilege() {
    return new SimplePrivilege("administration", "memberships", "read");
  }

  @Bean
  public Privilege membershipsWritePrivilege() {
    return new SimplePrivilege("administration", "memberships", "write");
  }

  @Bean
  public Privilege membershipsCreatePrivilege() {
    return new SimplePrivilege("administration", "memberships", "create");
  }

  @Bean
  public Privilege membershipsDeletePrivilege() {
    return new SimplePrivilege("administration", "memberships", "delete");
  }

}
