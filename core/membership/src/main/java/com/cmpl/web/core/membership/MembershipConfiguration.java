package com.cmpl.web.core.membership;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.models.Membership;

@Configuration
@EntityScan(basePackageClasses = {Membership.class})
@EnableJpaRepositories(basePackageClasses = {MembershipRepository.class})
public class MembershipConfiguration {

  @Bean
  public MembershipDAO membershipDAO(MembershipRepository membershipRepository, ApplicationEventPublisher publisher) {
    return new MembershipDAOImpl(membershipRepository, publisher);
  }

  @Bean
  public MembershipMapper membershipMapper() {
    return new MembershipMapper();
  }

  @Bean
  public MembershipService membershipService(MembershipDAO membershipDAO,
      MembershipMapper associationEntityGroupMapper) {
    return new MembershipServiceImpl(membershipDAO, associationEntityGroupMapper);
  }

  @Bean
  public MembershipTranslator membershipTranslator() {
    return new MembershipTranslatorImpl();
  }

  @Bean
  public MembershipDispatcher membershipDispatcher(MembershipService membershipService,
      MembershipTranslator membershipTranslator) {
    return new MembershipDispatcherImpl(membershipService, membershipTranslator);
  }

}
