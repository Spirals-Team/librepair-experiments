package com.cmpl.web.configuration.core.style;

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
import com.cmpl.web.core.factory.style.StyleDisplayFactoryImpl;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.style.StyleDispatcherImpl;
import com.cmpl.web.core.style.StyleRepository;
import com.cmpl.web.core.style.StyleService;
import com.cmpl.web.core.style.StyleServiceImpl;
import com.cmpl.web.core.style.StyleTranslator;
import com.cmpl.web.core.style.StyleTranslatorImpl;

@RunWith(MockitoJUnitRunner.class)
public class StyleConfigurationTest {

  @Mock
  private StyleRepository styleRepository;
  @Mock
  private MediaService mediaService;
  @Mock
  private FileService fileService;
  @Mock
  private StyleTranslator styleTranslator;
  @Mock
  private StyleService styleService;
  @Mock
  private MenuFactory menuFactory;
  @Mock
  private WebMessageSource messageSource;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry;
  @Mock
  private Set<Locale> availableLocales;

  @Mock
  private ApplicationEventPublisher publisher;

  @Spy
  @InjectMocks
  private StyleConfiguration configuration;

  @Test
  public void testStyleService() throws Exception {

    Assert.assertEquals(StyleServiceImpl.class,
        configuration.styleService(publisher, styleRepository, mediaService, fileService).getClass());
  }

  @Test
  public void testStyleDispacther() throws Exception {
    Assert.assertEquals(StyleDispatcherImpl.class,
        configuration.styleDispacther(styleService, styleTranslator).getClass());
  }

  @Test
  public void testStyleTranslator() throws Exception {
    Assert.assertEquals(StyleTranslatorImpl.class, configuration.styleTranslator().getClass());
  }

  @Test
  public void testStyleDisplayFactory() throws Exception {
    Assert.assertEquals(StyleDisplayFactoryImpl.class, configuration.styleDisplayFactory(menuFactory, messageSource,
        styleService, contextHolder, breadCrumbRegistry, availableLocales).getClass());
  }
}
