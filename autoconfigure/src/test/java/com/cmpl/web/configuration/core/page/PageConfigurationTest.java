package com.cmpl.web.configuration.core.page;

import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.factory.page.PageManagerDisplayFactoryImpl;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.page.PageDispatcherImpl;
import com.cmpl.web.core.page.PageRepository;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.page.PageServiceImpl;
import com.cmpl.web.core.page.PageTranslator;
import com.cmpl.web.core.page.PageTranslatorImpl;
import com.cmpl.web.core.page.PageValidator;
import com.cmpl.web.core.page.PageValidatorImpl;
import com.cmpl.web.core.widget.WidgetPageService;
import com.cmpl.web.core.widget.WidgetService;

@RunWith(MockitoJUnitRunner.class)
public class PageConfigurationTest {

  @Mock
  private ContextHolder contextHolder;
  @Mock
  private MenuFactory menuFactory;
  @Mock
  private WebMessageSource messageSource;
  @Mock
  private PageService pageService;
  @Mock
  private PageValidator validator;
  @Mock
  private PageTranslator translator;
  @Mock
  private PageRepository pageRepository;
  @Mock
  private FileService fileService;
  @Mock
  private WidgetService widgetService;
  @Mock
  private WidgetPageService widgetPageService;
  @Mock
  private PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry;
  @Mock
  private Set<Locale> availableLocales;

  @Mock
  private ApplicationEventPublisher publisher;

  @Spy
  @InjectMocks
  private PageConfiguration configuration;

  @Test
  public void testPageManagerDisplayFactory() throws Exception {

    Assert.assertEquals(PageManagerDisplayFactoryImpl.class,
        configuration.pageManagerDisplayFactory(contextHolder, menuFactory, messageSource, pageService, widgetService,
            widgetPageService, breadCrumbRegistry, availableLocales).getClass());
  }

  @Test
  public void testPageDispatcher() throws Exception {
    Assert.assertEquals(PageDispatcherImpl.class,
        configuration.pageDispatcher(validator, translator, pageService).getClass());
  }

  @Test
  public void testPageService() throws Exception {
    Assert.assertEquals(PageServiceImpl.class,
        configuration.pageService(publisher, pageRepository, fileService).getClass());
  }

  @Test
  public void testPageValidator() throws Exception {
    Assert.assertEquals(PageValidatorImpl.class, configuration.pageValidator(messageSource).getClass());
  }

  @Test
  public void testPageTranslator() throws Exception {
    Assert.assertEquals(PageTranslatorImpl.class, configuration.pageTranslator().getClass());
  }

}
