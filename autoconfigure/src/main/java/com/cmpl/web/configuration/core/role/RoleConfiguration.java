package com.cmpl.web.configuration.core.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.core.events_listeners.RoleEventsListeners;
import com.cmpl.web.core.association_user_role.AssociationUserRoleService;
import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.factory.role.RoleManagerDisplayFactory;
import com.cmpl.web.core.factory.role.RoleManagerDisplayFactoryImpl;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.role.Privilege;
import com.cmpl.web.core.role.PrivilegeRepository;
import com.cmpl.web.core.role.PrivilegeService;
import com.cmpl.web.core.role.PrivilegeServiceImpl;
import com.cmpl.web.core.role.Role;
import com.cmpl.web.core.role.RoleDispatcher;
import com.cmpl.web.core.role.RoleDispatcherImpl;
import com.cmpl.web.core.role.RoleRepository;
import com.cmpl.web.core.role.RoleService;
import com.cmpl.web.core.role.RoleServiceImpl;
import com.cmpl.web.core.role.RoleTranslator;
import com.cmpl.web.core.role.RoleTranslatorImpl;
import com.cmpl.web.core.role.RoleValidator;
import com.cmpl.web.core.role.RoleValidatorImpl;

@Configuration
@EntityScan(basePackageClasses = {Role.class, Privilege.class})
@EnableJpaRepositories(basePackageClasses = {RoleRepository.class, PrivilegeRepository.class})
public class RoleConfiguration {

  @Bean
  PrivilegeService privilegeService(ApplicationEventPublisher publisher, PrivilegeRepository privilegeRepository) {
    return new PrivilegeServiceImpl(publisher, privilegeRepository);
  }

  @Bean
  RoleService roleService(ApplicationEventPublisher publisher, RoleRepository entityRepository,
      PrivilegeService privilegeService) {
    return new RoleServiceImpl(publisher, entityRepository, privilegeService);
  }

  @Bean
  BackMenuItem roleBackMenuItem(BackMenuItem administration,
      com.cmpl.web.core.common.user.Privilege rolesReadPrivilege) {
    return BackMenuItemBuilder.create().href("back.roles.href").label("back.roles.label").title("back.roles.title")
        .iconClass("fa fa-tasks").parent(administration).order(1).privilege(rolesReadPrivilege.privilege()).build();
  }

  @Bean
  BreadCrumb roleBreadCrumb() {
    return BreadCrumbBuilder.create().items(roleBreadCrumbItems()).page(BACK_PAGE.ROLE_VIEW).build();
  }

  @Bean
  BreadCrumb roleUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(roleBreadCrumbItems()).page(BACK_PAGE.ROLE_UPDATE).build();
  }

  @Bean
  BreadCrumb roleCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(roleBreadCrumbItems()).page(BACK_PAGE.ROLE_CREATE).build();
  }

  List<BreadCrumbItem> roleBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.label").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.roles.title").href("back.roles.href").build());
    return items;
  }

  @Bean
  RoleTranslator roleTranslator() {
    return new RoleTranslatorImpl();
  }

  @Bean
  RoleValidator roleValidator(WebMessageSource messageSource) {
    return new RoleValidatorImpl(messageSource);
  }

  @Bean
  RoleDispatcher roleDispatcher(RoleService roleService, PrivilegeService privilegeService,
      RoleTranslator roleTranslator, RoleValidator roleValidator,
      @Qualifier(value = "privileges") PluginRegistry<com.cmpl.web.core.common.user.Privilege, String> privileges) {
    return new RoleDispatcherImpl(roleService, privilegeService, roleValidator, roleTranslator, privileges);
  }

  @Bean
  RoleManagerDisplayFactory roleManagerDisplayFactory(RoleService roleService, PrivilegeService privilegeService,
      ContextHolder contextHolder, MenuFactory menuFactory, WebMessageSource messageSource,
      @Qualifier(value = "breadCrumbs") PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry,
      @Qualifier(value = "privileges") PluginRegistry<com.cmpl.web.core.common.user.Privilege, String> privileges,
      Set<Locale> availableLocales) {
    return new RoleManagerDisplayFactoryImpl(roleService, privilegeService, contextHolder, menuFactory, messageSource,
        breadCrumbRegistry, privileges, availableLocales);
  }

  @Bean
  RoleEventsListeners roleEventsListener(AssociationUserRoleService associationUserRoleService,
      PrivilegeService privilegeService) {
    return new RoleEventsListeners(associationUserRoleService, privilegeService);
  }

}
