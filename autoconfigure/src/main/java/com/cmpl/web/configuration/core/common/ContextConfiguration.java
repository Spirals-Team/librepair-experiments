package com.cmpl.web.configuration.core.common;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.cmpl.web.core.association_user_role.AssociationUserRoleService;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.role.RoleService;
import com.cmpl.web.core.user.UserService;
import com.cmpl.web.manager.ui.core.security.CurrentUserDetailsServiceImpl;
import com.cmpl.web.manager.ui.core.user.LastConnectionUpdateAuthenticationSuccessHandlerImpl;

/**
 * COnfiguration du contextHolder a partir de donnes du fichier yaml
 * 
 * @author Louis
 *
 */
@Configuration
@PropertySource("classpath:/core/core.properties")
@EnablePluginRegistries({Privilege.class})
public class ContextConfiguration {

  @Value("${templateBasePath}")
  String templateBasePath;

  @Value("${mediaBasePath}")
  String mediaBasePath;

  @Value("${websiteUrl}")
  String websiteUrl;

  @Bean
  ContextHolder contextHolder() {

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy");

    ContextHolder contextHolder = new ContextHolder();

    contextHolder.setDateFormat(dateFormat);
    contextHolder.setElementsPerPage(10);
    contextHolder.setTemplateBasePath(templateBasePath);
    contextHolder.setMediaBasePath(mediaBasePath);
    contextHolder.setWebsiteUrl(websiteUrl);
    return contextHolder;

  }

  @Autowired
  @Qualifier(value = "privileges")
  private PluginRegistry<Privilege, String> privileges;

  @Bean
  public LastConnectionUpdateAuthenticationSuccessHandlerImpl lastConnectionUpdateAuthenticationSuccessHandler(
      UserService userService) {
    return new LastConnectionUpdateAuthenticationSuccessHandlerImpl(userService);
  }

  @Bean
  @Primary
  public UserDetailsService dbUserDetailsService(UserService userService, RoleService roleService,
      AssociationUserRoleService associationUserRoleService) {
    return new CurrentUserDetailsServiceImpl(userService, roleService, associationUserRoleService);
  }

}
