package com.cmpl.web.core.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;

import com.cmpl.web.core.common.user.Privilege;
import com.cmpl.web.core.models.Menu;
import com.cmpl.web.core.page.PageService;

@Configuration
@EntityScan(basePackageClasses = Menu.class)
@EnableJpaRepositories(basePackageClasses = MenuRepository.class)
@EnablePluginRegistries({BackMenuItemPlugin.class})
public class MenuConfiguration {

  @Bean
  public BackMenuItem menuBackMenuItem(BackMenuItem webmastering, Privilege menuReadPrivilege) {
    return BackMenuItemBuilder.create().href("/manager/menus").label("back.menus.label").title("back.menus.title")
        .order(3).iconClass("fa fa-list-alt").parent(webmastering).privilege(menuReadPrivilege.privilege()).build();
  }

  @Bean
  public MenuDAO menuDAO(ApplicationEventPublisher publisher, MenuRepository menuRepository) {
    return new MenuDAOImpl(menuRepository, publisher);
  }

  @Bean
  public MenuMapper menuMapper() {
    return new MenuMapper();
  }

  @Bean
  public MenuService menuService(MenuDAO menuDAO, MenuMapper menuMapper) {
    return new MenuServiceImpl(menuDAO, menuMapper);
  }

  @Bean
  public MenuTranslator menuTranslator() {
    return new MenuTranslatorImpl();
  }

  @Bean
  public MenuDispatcher menuDispatcher(MenuTranslator translator, MenuService menuService, PageService pageService) {
    return new MenuDispatcherImpl(translator, menuService, pageService);
  }

  @Autowired
  @Qualifier(value = "backMenus")
  private PluginRegistry<BackMenuItem, String> backMenus;

  @Bean
  public BackMenu backMenu() {
    return new BackMenu(backMenus);
  }

}
