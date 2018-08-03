package com.cmpl.web.configuration.manager.display;

import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.carousel.item.CarouselItemService;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.design.DesignService;
import com.cmpl.web.core.factory.carousel.CarouselManagerDisplayFactory;
import com.cmpl.web.core.factory.carousel.CarouselManagerDisplayFactoryImpl;
import com.cmpl.web.core.factory.group.GroupManagerDisplayFactory;
import com.cmpl.web.core.factory.group.GroupManagerDisplayFactoryImpl;
import com.cmpl.web.core.factory.index.IndexDisplayFactory;
import com.cmpl.web.core.factory.index.IndexDisplayFactoryImpl;
import com.cmpl.web.core.factory.login.LoginDisplayFactory;
import com.cmpl.web.core.factory.login.LoginDisplayFactoryImpl;
import com.cmpl.web.core.factory.media.MediaManagerDisplayFactory;
import com.cmpl.web.core.factory.media.MediaManagerDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.factory.menu.MenuFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuManagerDisplayFactory;
import com.cmpl.web.core.factory.menu.MenuManagerDisplayFactoryImpl;
import com.cmpl.web.core.factory.news.NewsManagerDisplayFactory;
import com.cmpl.web.core.factory.news.NewsManagerDisplayFactoryImpl;
import com.cmpl.web.core.factory.page.PageManagerDisplayFactory;
import com.cmpl.web.core.factory.page.PageManagerDisplayFactoryImpl;
import com.cmpl.web.core.factory.role.RoleManagerDisplayFactory;
import com.cmpl.web.core.factory.role.RoleManagerDisplayFactoryImpl;
import com.cmpl.web.core.factory.style.StyleDisplayFactory;
import com.cmpl.web.core.factory.style.StyleDisplayFactoryImpl;
import com.cmpl.web.core.factory.user.UserManagerDisplayFactory;
import com.cmpl.web.core.factory.user.UserManagerDisplayFactoryImpl;
import com.cmpl.web.core.factory.website.WebsiteManagerDisplayFactory;
import com.cmpl.web.core.factory.website.WebsiteManagerDisplayFactoryImpl;
import com.cmpl.web.core.factory.widget.WidgetManagerDisplayFactory;
import com.cmpl.web.core.factory.widget.WidgetManagerDisplayFactoryImpl;
import com.cmpl.web.core.group.GroupService;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.membership.MembershipService;
import com.cmpl.web.core.menu.BackMenu;
import com.cmpl.web.core.menu.MenuService;
import com.cmpl.web.core.news.entry.NewsEntryService;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.responsibility.ResponsibilityService;
import com.cmpl.web.core.role.RoleService;
import com.cmpl.web.core.role.privilege.PrivilegeService;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.style.StyleService;
import com.cmpl.web.core.user.UserService;
import com.cmpl.web.core.website.WebsiteService;
import com.cmpl.web.core.widget.WidgetService;
import com.cmpl.web.core.widget.page.WidgetPageService;

@Configuration
public class DisplayFactoryConfiguration {

  @Bean
  public CarouselManagerDisplayFactory carouselManagerDisplayFactory(MenuFactory menuFactory,
      WebMessageSource messageSource, CarouselService carouselService, CarouselItemService carouselItemService,
      MediaService mediaService, ContextHolder contextHolder, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs,
      Set<Locale> availableLocales, GroupService groupService, MembershipService membershipService) {
    return new CarouselManagerDisplayFactoryImpl(menuFactory, messageSource, carouselService, carouselItemService,
        mediaService, contextHolder, breadCrumbs, availableLocales, groupService, membershipService);
  }

  @Bean
  public GroupManagerDisplayFactory groupManagerDisplayFactory(GroupService groupService, ContextHolder contextHolder,
      MenuFactory menuFactory, WebMessageSource messageSource,
      @Qualifier(value = "breadCrumbs") PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry,
      @Qualifier(value = "privileges") PluginRegistry<com.cmpl.web.core.common.user.Privilege, String> privileges,
      Set<Locale> availableLocales, MembershipService membershipService) {
    return new GroupManagerDisplayFactoryImpl(groupService, contextHolder, menuFactory, messageSource,
        breadCrumbRegistry, privileges, availableLocales, membershipService);
  }

  @Bean
  public MediaManagerDisplayFactory mediaManagerDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      MediaService mediaService, ContextHolder contextHolder, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs,
      Set<Locale> availableLocales, GroupService groupService, MembershipService membershipService) {
    return new MediaManagerDisplayFactoryImpl(menuFactory, messageSource, mediaService, contextHolder, breadCrumbs,
        availableLocales, groupService, membershipService);
  }

  @Bean
  public MenuManagerDisplayFactory menuManagerDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      MenuService menuService, PageService pageService, ContextHolder contextHolder,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs, Set<Locale> availableLocales, GroupService groupService,
      MembershipService membershipService) {
    return new MenuManagerDisplayFactoryImpl(menuFactory, messageSource, menuService, pageService, contextHolder,
        breadCrumbs, availableLocales, groupService, membershipService);
  }

  @Bean
  public MenuFactory menuFactory(WebMessageSource messageSource, MenuService menuService, BackMenu backMenu) {
    return new MenuFactoryImpl(messageSource, menuService, backMenu);
  }

  @Bean
  public NewsManagerDisplayFactory newsManagerDisplayFactory(ContextHolder contextHolder, MenuFactory menuFactory,
      WebMessageSource messageSource, NewsEntryService newsEntryService,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs, Set<Locale> availableLocales, GroupService groupService,
      MembershipService membershipService) {
    return new NewsManagerDisplayFactoryImpl(contextHolder, menuFactory, messageSource, newsEntryService, breadCrumbs,
        availableLocales, groupService, membershipService);
  }

  @Bean
  public PageManagerDisplayFactory pageManagerDisplayFactory(ContextHolder contextHolder, MenuFactory menuFactory,
      WebMessageSource messageSource, PageService pageService, WidgetService widgetService,
      WidgetPageService widgetPageService, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs,
      Set<Locale> availableLocales, GroupService groupService, MembershipService membershipService) {
    return new PageManagerDisplayFactoryImpl(menuFactory, messageSource, pageService, contextHolder, widgetService,
        widgetPageService, breadCrumbs, availableLocales, groupService, membershipService);
  }

  @Bean
  public RoleManagerDisplayFactory roleManagerDisplayFactory(RoleService roleService, PrivilegeService privilegeService,
      ContextHolder contextHolder, MenuFactory menuFactory, WebMessageSource messageSource,
      @Qualifier(value = "breadCrumbs") PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry,
      @Qualifier(value = "privileges") PluginRegistry<com.cmpl.web.core.common.user.Privilege, String> privileges,
      Set<Locale> availableLocales, GroupService groupService, MembershipService membershipService) {
    return new RoleManagerDisplayFactoryImpl(roleService, privilegeService, contextHolder, menuFactory, messageSource,
        breadCrumbRegistry, privileges, availableLocales, groupService, membershipService);
  }

  @Bean
  public StyleDisplayFactory styleDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      StyleService styleService, ContextHolder contextHolder, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs,
      Set<Locale> availableLocales, GroupService groupService, MembershipService membershipService) {
    return new StyleDisplayFactoryImpl(menuFactory, messageSource, styleService, contextHolder, breadCrumbs,
        availableLocales, groupService, membershipService);
  }

  @Bean
  public UserManagerDisplayFactory userManagerDisplayFactory(UserService userService, RoleService roleService,
      ResponsibilityService responsibilityService, ContextHolder contextHolder, MenuFactory menuFactory,
      WebMessageSource messageSource, PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs, Set<Locale> availableLocales,
      GroupService groupService, MembershipService membershipService) {
    return new UserManagerDisplayFactoryImpl(userService, roleService, responsibilityService, contextHolder,
        menuFactory, messageSource, breadCrumbs, availableLocales, groupService, membershipService);
  }

  @Bean
  public WidgetManagerDisplayFactory widgetManagerDisplayFactory(MenuFactory menuFactory,
      WebMessageSource messageSource, WidgetService widgetService, ContextHolder contextHolder,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs, PluginRegistry<WidgetProviderPlugin, String> widgetProviders,
      Set<Locale> availableLocales, GroupService groupService, MembershipService membershipService) {
    return new WidgetManagerDisplayFactoryImpl(menuFactory, messageSource, contextHolder, widgetService, breadCrumbs,
        widgetProviders, availableLocales, groupService, membershipService);
  }

  @Bean
  public LoginDisplayFactory loginDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs, Set<Locale> availableLocales, GroupService groupService,
      MembershipService membershipService) {
    return new LoginDisplayFactoryImpl(menuFactory, messageSource, breadCrumbs, availableLocales, groupService,
        membershipService);
  }

  @Bean
  public IndexDisplayFactory indexDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs, Set<Locale> availableLocales, GroupService groupService,
      MembershipService membershipService) {
    return new IndexDisplayFactoryImpl(menuFactory, messageSource, breadCrumbs, availableLocales, groupService,
        membershipService);
  }

  @Bean
  public WebsiteManagerDisplayFactory websiteManagerDisplayFactory(WebsiteService websiteService,
      ContextHolder contextHolder, MenuFactory menuFactory, WebMessageSource messageSource,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs, Set<Locale> availableLocales, GroupService groupService,
      MembershipService membershipService, DesignService designService, SitemapService sitemapService,
      PageService pageService, StyleService styleService) {
    return new WebsiteManagerDisplayFactoryImpl(menuFactory, messageSource, breadCrumbs, availableLocales, groupService,
        membershipService, contextHolder, websiteService, designService, sitemapService, pageService, styleService);
  }

}
