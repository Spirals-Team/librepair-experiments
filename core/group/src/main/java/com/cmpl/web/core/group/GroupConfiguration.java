package com.cmpl.web.core.group;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.models.BOGroup;

@Configuration
@EntityScan(basePackageClasses = {BOGroup.class})
@EnableJpaRepositories(basePackageClasses = {GroupRepository.class})
public class GroupConfiguration {

  @Bean
  public GroupMapper groupMapper() {
    return new GroupMapper();
  }

  @Bean
  public GroupDAO groupDAO(ApplicationEventPublisher publisher, GroupRepository groupRepository) {
    return new GroupDAOImpl(groupRepository, publisher);
  }

  @Bean
  public GroupService groupService(GroupDAO groupDAO, GroupMapper groupMapper) {
    return new GroupServiceImpl(groupDAO, groupMapper);
  }

  @Bean
  public GroupTranslator groupTranslator() {
    return new GroupTranslatorImpl();
  }

  @Bean
  public GroupDispatcher groupDispatcher(GroupService groupService, GroupTranslator groupTranslator) {
    return new GroupDispatcherImpl(groupTranslator, groupService);
  }

}
