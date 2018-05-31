package com.cmpl.web.configuration.core.carousel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.common.user.SimplePrivilege;

@Configuration
public class CarouselPrivilegeConfiguration {

  @Bean
  public Privilege carouselsReadPrivilege() {
    return new SimplePrivilege("webmastering", "carousels", "read");
  }

  @Bean
  public Privilege carouselsWritePrivilege() {
    return new SimplePrivilege("webmastering", "carousels", "write");
  }

  @Bean
  public Privilege carouselsCreatePrivilege() {
    return new SimplePrivilege("webmastering", "carousels", "create");
  }

  @Bean
  public Privilege carouselsDeletePrivilege() {
    return new SimplePrivilege("webmastering", "carousels", "delete");
  }

}
