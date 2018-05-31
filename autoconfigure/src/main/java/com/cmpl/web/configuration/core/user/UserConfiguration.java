package com.cmpl.web.configuration.core.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cmpl.core.events_listeners.UserEventsListeners;
import com.cmpl.web.core.association_user_role.AssociationUserRoleService;
import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.breadcrumb.BreadCrumbBuilder;
import com.cmpl.web.core.breadcrumb.BreadCrumbItem;
import com.cmpl.web.core.breadcrumb.BreadCrumbItemBuilder;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.mail.MailSender;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.user.ActionTokenService;
import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.factory.user.UserManagerDisplayFactory;
import com.cmpl.web.core.factory.user.UserManagerDisplayFactoryImpl;
import com.cmpl.web.core.menu.BackMenuItem;
import com.cmpl.web.core.menu.BackMenuItemBuilder;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.role.RoleService;
import com.cmpl.web.core.user.User;
import com.cmpl.web.core.user.UserDispatcher;
import com.cmpl.web.core.user.UserDispatcherImpl;
import com.cmpl.web.core.user.UserMailService;
import com.cmpl.web.core.user.UserMailServiceImpl;
import com.cmpl.web.core.user.UserRepository;
import com.cmpl.web.core.user.UserService;
import com.cmpl.web.core.user.UserServiceImpl;
import com.cmpl.web.core.user.UserTranslator;
import com.cmpl.web.core.user.UserTranslatorImpl;
import com.cmpl.web.core.user.UserValidator;
import com.cmpl.web.core.user.UserValidatorImpl;

@Configuration
@EntityScan(basePackageClasses = User.class)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class UserConfiguration {

  @Bean
  UserService userService(ApplicationEventPublisher publisher, UserRepository userRepository,
      ActionTokenService actionTokenService, UserMailService userMailService) {
    return new UserServiceImpl(publisher, actionTokenService, userMailService, userRepository);
  }

  @Bean
  UserMailService userMailService(MailSender mailSender) {
    return new UserMailServiceImpl(mailSender);
  }

  @Bean
  BackMenuItem userBackMenuItem(BackMenuItem administration, Privilege usersReadPrivilege) {
    return BackMenuItemBuilder.create().href("back.users.href").label("back.users.label").title("back.users.title")
        .iconClass("fa fa-user").parent(administration).privilege(usersReadPrivilege.privilege()).order(0).build();
  }

  @Bean
  BreadCrumb userBreadCrumb() {
    return BreadCrumbBuilder.create().items(userBreadCrumbItems()).page(BACK_PAGE.USER_VIEW).build();
  }

  @Bean
  BreadCrumb userUpdateBreadCrumb() {
    return BreadCrumbBuilder.create().items(userBreadCrumbItems()).page(BACK_PAGE.USER_UPDATE).build();
  }

  @Bean
  BreadCrumb userCreateBreadCrumb() {
    return BreadCrumbBuilder.create().items(userBreadCrumbItems()).page(BACK_PAGE.USER_CREATE).build();
  }

  List<BreadCrumbItem> userBreadCrumbItems() {
    List<BreadCrumbItem> items = new ArrayList<>();
    items.add(BreadCrumbItemBuilder.create().text("back.index.label").href("back.index.href").build());
    items.add(BreadCrumbItemBuilder.create().text("back.users.title").href("back.users.href").build());
    return items;
  }

  @Bean
  UserTranslator userTranslator() {
    return new UserTranslatorImpl();
  }

  @Bean
  UserValidator userValidator(WebMessageSource messageSource) {
    return new UserValidatorImpl(messageSource);
  }

  @Bean
  UserDispatcher userDispatcher(UserTranslator userTranslator, UserValidator userValidator, UserService userService,
      PasswordEncoder passwordEncoder, ActionTokenService tokenService) {
    return new UserDispatcherImpl(userValidator, userTranslator, userService, passwordEncoder, tokenService);
  }

  @Bean
  UserManagerDisplayFactory userManagerDisplayFactory(UserService userService, RoleService roleService,
      AssociationUserRoleService associationUserRoleService, ContextHolder contextHolder, MenuFactory menuFactory,
      WebMessageSource messageSource, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs, Set<Locale> availableLocales) {
    return new UserManagerDisplayFactoryImpl(userService, roleService, associationUserRoleService, contextHolder,
        menuFactory, messageSource, breadCrumbs, availableLocales);
  }

  @Bean
  UserEventsListeners userEventsListener(AssociationUserRoleService associationUserRoleService) {
    return new UserEventsListeners(associationUserRoleService);
  }
}
