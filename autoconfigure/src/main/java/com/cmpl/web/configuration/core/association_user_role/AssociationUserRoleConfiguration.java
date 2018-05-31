package com.cmpl.web.configuration.core.association_user_role;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cmpl.web.core.association_user_role.AssociationUserRole;
import com.cmpl.web.core.association_user_role.AssociationUserRoleDispatcher;
import com.cmpl.web.core.association_user_role.AssociationUserRoleDispatcherImpl;
import com.cmpl.web.core.association_user_role.AssociationUserRoleRepository;
import com.cmpl.web.core.association_user_role.AssociationUserRoleService;
import com.cmpl.web.core.association_user_role.AssociationUserRoleServiceImpl;
import com.cmpl.web.core.association_user_role.AssociationUserRoleTranslator;
import com.cmpl.web.core.association_user_role.AssociationUserRoleTranslatorImpl;
import com.cmpl.web.core.association_user_role.AssociationUserRoleValidator;
import com.cmpl.web.core.association_user_role.AssociationUserRoleValidatorImpl;
import com.cmpl.web.core.common.message.WebMessageSource;

@Configuration
@EntityScan(basePackageClasses = AssociationUserRole.class)
@EnableJpaRepositories(basePackageClasses = AssociationUserRoleRepository.class)
public class AssociationUserRoleConfiguration {

  @Bean
  AssociationUserRoleService associationUserRoleService(ApplicationEventPublisher publisher,
      AssociationUserRoleRepository associationUserRoleRepository) {
    return new AssociationUserRoleServiceImpl(publisher, associationUserRoleRepository);
  }

  @Bean
  AssociationUserRoleValidator associationUserRoleValidator(WebMessageSource messageSource) {
    return new AssociationUserRoleValidatorImpl(messageSource);
  }

  @Bean
  AssociationUserRoleTranslator associationUserRoleTranslator() {
    return new AssociationUserRoleTranslatorImpl();
  }

  @Bean
  AssociationUserRoleDispatcher associationUserRoleDispatcher(AssociationUserRoleService associationUserRoleService,
      AssociationUserRoleValidator associationUserRoleValidator,
      AssociationUserRoleTranslator associationUserRoleTranslator) {
    return new AssociationUserRoleDispatcherImpl(associationUserRoleService, associationUserRoleValidator,
        associationUserRoleTranslator);
  }

}
