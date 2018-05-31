package com.cmpl.web.configuration.manager.ui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cmpl.web.core.association_user_role.AssociationUserRoleDispatcher;
import com.cmpl.web.core.carousel.CarouselDispatcher;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.common.notification.NotificationCenter;
import com.cmpl.web.core.factory.BackDisplayFactory;
import com.cmpl.web.core.factory.carousel.CarouselManagerDisplayFactory;
import com.cmpl.web.core.factory.login.LoginDisplayFactory;
import com.cmpl.web.core.factory.media.MediaManagerDisplayFactory;
import com.cmpl.web.core.factory.menu.MenuManagerDisplayFactory;
import com.cmpl.web.core.factory.news.NewsManagerDisplayFactory;
import com.cmpl.web.core.factory.page.PageManagerDisplayFactory;
import com.cmpl.web.core.factory.role.RoleManagerDisplayFactory;
import com.cmpl.web.core.factory.style.StyleDisplayFactory;
import com.cmpl.web.core.factory.user.UserManagerDisplayFactory;
import com.cmpl.web.core.factory.widget.WidgetManagerDisplayFactory;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.menu.MenuDispatcher;
import com.cmpl.web.core.news.NewsEntryDispatcher;
import com.cmpl.web.core.page.PageDispatcher;
import com.cmpl.web.core.role.RoleDispatcher;
import com.cmpl.web.core.style.StyleDispatcher;
import com.cmpl.web.core.user.UserDispatcher;
import com.cmpl.web.core.widget.WidgetDispatcher;
import com.cmpl.web.facebook.FacebookDispatcher;
import com.cmpl.web.manager.ui.core.association_user_role.AssociationUserRoleManagerController;
import com.cmpl.web.manager.ui.core.carousel.CarouselManagerController;
import com.cmpl.web.manager.ui.core.index.IndexManagerController;
import com.cmpl.web.manager.ui.core.login.LoginController;
import com.cmpl.web.manager.ui.core.media.MediaManagerController;
import com.cmpl.web.manager.ui.core.menu.MenuManagerController;
import com.cmpl.web.manager.ui.core.news.NewsManagerController;
import com.cmpl.web.manager.ui.core.page.PageManagerController;
import com.cmpl.web.manager.ui.core.role.RoleManagerController;
import com.cmpl.web.manager.ui.core.style.StyleManagerController;
import com.cmpl.web.manager.ui.core.user.CurrentUserControllerAdvice;
import com.cmpl.web.manager.ui.core.user.UserManagerController;
import com.cmpl.web.manager.ui.core.widget.WidgetManagerController;
import com.cmpl.web.manager.ui.core.widget.WidgetPageManagerController;
import com.cmpl.web.manager.ui.modules.facebook.FacebookController;
import com.cmpl.web.modules.facebook.factory.FacebookDisplayFactory;

@Configuration
public class BackControllerConfiguration {

  @Bean
  public FacebookController facebookController(FacebookDisplayFactory facebookDisplayFactory,
      FacebookDispatcher facebookDispatcher, NotificationCenter notificationCenter, WebMessageSource messageSource) {
    return new FacebookController(facebookDisplayFactory, facebookDispatcher, notificationCenter, messageSource);
  }

  @Bean
  public CarouselManagerController carouselManagerController(CarouselDispatcher carouselDispatcher,
      CarouselManagerDisplayFactory carouselDisplayFactory, NotificationCenter notificationCenter,
      WebMessageSource messageSource) {
    return new CarouselManagerController(carouselDispatcher, carouselDisplayFactory, notificationCenter, messageSource);
  }

  @Bean
  public IndexManagerController indexManagerController(BackDisplayFactory loginDisplayFactory) {
    return new IndexManagerController(loginDisplayFactory);
  }

  @Bean
  public LoginController loginController(LoginDisplayFactory loginDisplayFactory, UserDispatcher userDispatcher) {
    return new LoginController(loginDisplayFactory, userDispatcher);
  }

  @Bean
  public MediaManagerController mediaManagerController(MediaService mediaService,
      MediaManagerDisplayFactory mediaManagerDisplayFactory, NotificationCenter notificationCenter,
      WebMessageSource messageSource) {
    return new MediaManagerController(mediaService, mediaManagerDisplayFactory, notificationCenter, messageSource);
  }

  @Bean
  public MenuManagerController menuManagerController(MenuDispatcher dispatcher,
      MenuManagerDisplayFactory displayFactory, NotificationCenter notificationCenter, WebMessageSource messageSource) {
    return new MenuManagerController(dispatcher, displayFactory, notificationCenter, messageSource);
  }

  @Bean
  public NewsManagerController newsManagerController(NewsManagerDisplayFactory newsManagerDisplayFactory,
      NewsEntryDispatcher dispatcher, NotificationCenter notificationCenter, WebMessageSource messageSource) {
    return new NewsManagerController(newsManagerDisplayFactory, dispatcher, notificationCenter, messageSource);
  }

  @Bean
  public PageManagerController pageManagerController(PageManagerDisplayFactory pageManagerDisplayFactory,
      PageDispatcher pageDispatcher, NotificationCenter notificationCenter, WebMessageSource messageSource) {
    return new PageManagerController(pageManagerDisplayFactory, pageDispatcher, notificationCenter, messageSource);
  }

  @Bean
  public StyleManagerController styleManagerController(StyleDisplayFactory displayFactory, StyleDispatcher dispatcher,
      NotificationCenter notificationCenter, WebMessageSource messageSource) {
    return new StyleManagerController(displayFactory, dispatcher, notificationCenter, messageSource);
  }

  @Bean
  public WidgetManagerController widgetManagerController(WidgetManagerDisplayFactory widgetManagerDisplayFactory,
      WidgetDispatcher widgetDispatcher, NotificationCenter notificationCenter, WebMessageSource messageSource) {
    return new WidgetManagerController(widgetManagerDisplayFactory, widgetDispatcher, notificationCenter,
        messageSource);
  }

  @Bean
  public WidgetPageManagerController widgetPageManagerController(WidgetDispatcher widgetDispatcher,
      NotificationCenter notificationCenter, WebMessageSource messageSource) {
    return new WidgetPageManagerController(widgetDispatcher, notificationCenter, messageSource);
  }

  @Bean
  public CurrentUserControllerAdvice currentUserControllerAdvice() {
    return new CurrentUserControllerAdvice();
  }

  @Bean
  public UserManagerController userManagerController(UserManagerDisplayFactory userManagerDisplayFactory,
      UserDispatcher userDispatcher, NotificationCenter notificationCenter, WebMessageSource messageSource) {
    return new UserManagerController(userManagerDisplayFactory, userDispatcher, notificationCenter, messageSource);
  }

  @Bean
  public RoleManagerController roleManagerController(RoleManagerDisplayFactory roleManagerDisplayFactory,
      RoleDispatcher roleDispatcher, NotificationCenter notificationCenter, WebMessageSource messageSource) {
    return new RoleManagerController(roleDispatcher, roleManagerDisplayFactory, notificationCenter, messageSource);
  }

  @Bean
  AssociationUserRoleManagerController associationUserRoleManagerController(
      AssociationUserRoleDispatcher associationUserRoleDispatcher, NotificationCenter notificationCenter,
      WebMessageSource messageSource) {
    return new AssociationUserRoleManagerController(associationUserRoleDispatcher, notificationCenter, messageSource);
  }

}
