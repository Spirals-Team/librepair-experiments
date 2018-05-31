package com.cmpl.web.launcher;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cmpl.web.configuration.EnableCMPLWeb;
import com.cmpl.web.core.association_user_role.AssociationUserRole;
import com.cmpl.web.core.association_user_role.AssociationUserRoleBuilder;
import com.cmpl.web.core.association_user_role.AssociationUserRoleRepository;
import com.cmpl.web.core.carousel.CarouselItemRepository;
import com.cmpl.web.core.carousel.CarouselRepository;
import com.cmpl.web.core.media.MediaRepository;
import com.cmpl.web.core.menu.MenuRepository;
import com.cmpl.web.core.news.NewsContentRepository;
import com.cmpl.web.core.news.NewsEntryRepository;
import com.cmpl.web.core.page.PageRepository;
import com.cmpl.web.core.role.Privilege;
import com.cmpl.web.core.role.PrivilegeBuilder;
import com.cmpl.web.core.role.PrivilegeRepository;
import com.cmpl.web.core.role.Role;
import com.cmpl.web.core.role.RoleBuilder;
import com.cmpl.web.core.role.RoleRepository;
import com.cmpl.web.core.user.User;
import com.cmpl.web.core.user.UserBuilder;
import com.cmpl.web.core.user.UserRepository;
import com.cmpl.web.core.widget.WidgetPageRepository;
import com.cmpl.web.core.widget.WidgetRepository;

/**
 * Main du projet, lance une application springboot
 * 
 * @author Louis
 */
@SpringBootApplication
@EnableCMPLWeb
public class WebLauncher {

  /**
   * Main du projet, lance un SpringApplication
   * 
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(WebLauncher.class, args);
  }

  @Bean
  @Profile("dev")
  public CommandLineRunner init(final NewsEntryRepository newsEntryRepository,
      final NewsContentRepository newsContentRepository, final PageRepository pageRepository,
      final MenuRepository menuRepository, final CarouselRepository carouselRepository,
      final CarouselItemRepository carouselItemRepository, final MediaRepository mediaRepository,
      final WidgetRepository widgetRepository, final WidgetPageRepository widgetPageRepository,
      final UserRepository userRepository, final RoleRepository roleRepository,
      final PrivilegeRepository privilegeRepository, final AssociationUserRoleRepository associationUserRoleRepository,
      final PasswordEncoder passwordEncoder,
      final PluginRegistry<com.cmpl.web.core.common.user.Privilege, String> privileges) {
    return (args) -> {

      NewsFactory.createNewsEntries(newsEntryRepository, newsContentRepository);

      PageFactory.createPages(pageRepository, menuRepository, carouselRepository, carouselItemRepository,
          mediaRepository, widgetRepository, widgetPageRepository);

      User system = UserBuilder.create().login("system").email("lperrod@cardiweb.com").description("system")
          .lastConnection(LocalDateTime.now()).lastPasswordModification(LocalDateTime.now().minusMonths(1))
          .password(passwordEncoder.encode("system")).build();
      system = userRepository.save(system);
      Role admin = RoleBuilder.create().description("admin").name("admin").build();
      final Role createdAdmin = roleRepository.save(admin);

      AssociationUserRole associationSystemAdmin = AssociationUserRoleBuilder.create()
          .roleId(String.valueOf(admin.getId())).userId(String.valueOf(system.getId())).build();
      associationUserRoleRepository.save(associationSystemAdmin);

      privileges.getPlugins().forEach(
          privilege -> {
            Privilege privilegeToCreate = PrivilegeBuilder.create().roleId(String.valueOf(createdAdmin.getId()))
                .content(privilege.privilege()).build();
            privilegeRepository.save(privilegeToCreate);
          });

    };
  }

}
