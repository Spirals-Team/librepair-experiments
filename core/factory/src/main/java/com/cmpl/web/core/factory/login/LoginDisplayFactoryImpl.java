package com.cmpl.web.core.factory.login;

import java.util.Locale;
import java.util.Set;

import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.BackDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.page.BACK_PAGE;

/**
 * Implementation de l'interface pour la factory de la page de login
 * 
 * @author Louis
 *
 */
public class LoginDisplayFactoryImpl extends BackDisplayFactoryImpl implements LoginDisplayFactory {

  public LoginDisplayFactoryImpl(MenuFactory menuFactory, WebMessageSource messageSource,
      PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry, Set<Locale> availableLocales) {
    super(menuFactory, messageSource, breadCrumbRegistry, availableLocales);
  }

}
