package com.cmpl.web.configuration.core.login;

import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.message.WebMessageSourceImpl;
import com.cmpl.web.core.factory.login.LoginDisplayFactory;
import com.cmpl.web.core.factory.login.LoginDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.menu.MenuService;
import com.cmpl.web.core.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class LoginConfigurationTest {

  @Mock
  private MenuFactory menuFactory;

  @Mock
  private WebMessageSourceImpl messageSource;

  @Mock
  private MenuService menuService;

  @Mock
  private PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry;

  @Mock
  private Set<Locale> availableLocales;

  @Spy
  LoginConfiguration configuration;

  @Test
  public void testLogindisplayFactory() throws Exception {
    LoginDisplayFactory result = configuration.loginDisplayFactory(menuFactory, messageSource, breadCrumbRegistry,
        availableLocales);

    Assert.assertEquals(LoginDisplayFactoryImpl.class, result.getClass());
  }
}
