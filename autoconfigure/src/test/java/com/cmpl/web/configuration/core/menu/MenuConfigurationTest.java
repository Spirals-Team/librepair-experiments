package com.cmpl.web.configuration.core.menu;

import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSourceImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.factory.menu.MenuFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuManagerDisplayFactoryImpl;
import com.cmpl.web.core.menu.BackMenu;
import com.cmpl.web.core.menu.MenuDispatcherImpl;
import com.cmpl.web.core.menu.MenuService;
import com.cmpl.web.core.menu.MenuTranslator;
import com.cmpl.web.core.menu.MenuTranslatorImpl;
import com.cmpl.web.core.menu.MenuValidator;
import com.cmpl.web.core.menu.MenuValidatorImpl;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.page.PageService;

@RunWith(MockitoJUnitRunner.class)
public class MenuConfigurationTest {

  @Mock
  private WebMessageSourceImpl messageSource;

  @Mock
  private MenuService menuService;
  @Mock
  private MenuValidator validator;
  @Mock
  private MenuTranslator translator;
  @Mock
  private PageService pageService;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private MenuFactory menuFactory;
  @Mock
  private BackMenu backMenu;
  @Mock
  private PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry;
  @Mock
  private Set<Locale> availableLocales;

  @Spy
  @InjectMocks
  MenuConfiguration configuration;

  @Test
  public void testMenuFactory() throws Exception {
    Assert.assertEquals(MenuFactoryImpl.class,
        configuration.menuFactory(messageSource, menuService, backMenu).getClass());

  }

  @Test
  public void testMenuValidator() throws Exception {
    Assert.assertEquals(MenuValidatorImpl.class, configuration.menuValidator(messageSource).getClass());
  }

  @Test
  public void testMenuTranslator() throws Exception {
    Assert.assertEquals(MenuTranslatorImpl.class, configuration.menuTranslator().getClass());
  }

  @Test
  public void testMenuDispatcher() throws Exception {
    Assert.assertEquals(MenuDispatcherImpl.class,
        configuration.menuDispatcher(validator, translator, menuService, pageService).getClass());
  }

  @Test
  public void testMenuManagerDisplayFactory() throws Exception {
    Assert.assertEquals(MenuManagerDisplayFactoryImpl.class, configuration.menuManagerDisplayFactory(menuFactory,
        messageSource, menuService, pageService, contextHolder, breadCrumbRegistry, availableLocales).getClass());
    ;
  }

}
