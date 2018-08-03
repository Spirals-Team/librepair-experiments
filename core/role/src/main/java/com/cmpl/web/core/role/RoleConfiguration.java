package com.cmpl.web.core.role;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.models.Privilege;
import com.cmpl.web.core.models.Role;
import com.cmpl.web.core.role.privilege.PrivilegeDAO;
import com.cmpl.web.core.role.privilege.PrivilegeDAOImpl;
import com.cmpl.web.core.role.privilege.PrivilegeMapper;
import com.cmpl.web.core.role.privilege.PrivilegeRepository;
import com.cmpl.web.core.role.privilege.PrivilegeService;
import com.cmpl.web.core.role.privilege.PrivilegeServiceImpl;

@Configuration
@EntityScan(basePackageClasses = {Role.class, Privilege.class})
@EnableJpaRepositories(basePackageClasses = {RoleRepository.class, PrivilegeRepository.class})
public class RoleConfiguration {

  @Bean
  public PrivilegeMapper privilegeMapper() {
    return new PrivilegeMapper();
  }

  @Bean
  public PrivilegeDAO privilegeDAO(ApplicationEventPublisher publisher, PrivilegeRepository privilegeRepository) {
    return new PrivilegeDAOImpl(privilegeRepository, publisher);
  }

  @Bean
  public PrivilegeService privilegeService(PrivilegeDAO privilegeDAO, PrivilegeMapper privilegeMapper) {
    return new PrivilegeServiceImpl(privilegeDAO, privilegeMapper);
  }

  @Bean
  public RoleMapper roleMapper(PrivilegeService privilegeService) {
    return new RoleMapper(privilegeService);
  }

  @Bean
  public RoleDAO roleDAO(ApplicationEventPublisher publisher, RoleRepository roleRepository) {
    return new RoleDAOImpl(roleRepository, publisher);
  }

  @Bean
  public RoleService roleService(RoleDAO roleDAO, RoleMapper roleMapper) {
    return new RoleServiceImpl(roleDAO, roleMapper);
  }

  @Bean
  public RoleTranslator roleTranslator() {
    return new RoleTranslatorImpl();
  }

  @Bean
  public RoleDispatcher roleDispatcher(RoleService roleService, PrivilegeService privilegeService,
      RoleTranslator roleTranslator,
      @Qualifier(value = "privileges") PluginRegistry<com.cmpl.web.core.common.user.Privilege, String> privileges) {
    return new RoleDispatcherImpl(roleService, privilegeService, roleTranslator, privileges);
  }

}
