package com.cmpl.web.configuration.core.login;

import java.util.Locale;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.login.LoginDisplayFactory;
import com.cmpl.web.core.factory.login.LoginDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.page.BACK_PAGE;

@Configuration
public class LoginConfiguration {

  @Bean
  public LoginDisplayFactory loginDisplayFactory(MenuFactory menuFactory, WebMessageSource messageSource,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbs, Set<Locale> availableLocales) {
    return new LoginDisplayFactoryImpl(menuFactory, messageSource, breadCrumbs, availableLocales);
  }

}
