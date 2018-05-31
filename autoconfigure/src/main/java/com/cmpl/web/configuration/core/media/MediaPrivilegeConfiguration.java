package com.cmpl.web.configuration.core.media;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;

@Configuration
public class MediaPrivilegeConfiguration {

  @Bean
  public Privilege mediaReadPrivilege() {
    return new SimplePrivilege("webmastering", "media", "read");
  }

  @Bean
  public Privilege mediaWritePrivilege() {
    return new SimplePrivilege("webmastering", "media", "write");
  }

  @Bean
  public Privilege mediaCreatePrivilege() {
    return new SimplePrivilege("webmastering", "media", "create");
  }

  @Bean
  public Privilege mediaDeletePrivilege() {
    return new SimplePrivilege("webmastering", "media", "delete");
  }
}
