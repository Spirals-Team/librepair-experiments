package com.cmpl.web.configuration.core.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.carousel.CarouselService;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSourceImpl;
import com.cmpl.web.core.factory.DisplayFactory;
import com.cmpl.web.core.factory.DisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.page.PageService;
import com.cmpl.web.core.provider.WidgetProviderPlugin;
import com.cmpl.web.core.widget.WidgetPageService;
import com.cmpl.web.core.widget.WidgetService;

@RunWith(MockitoJUnitRunner.class)
public class FactoryConfigurationTest {

  @Mock
  private MenuFactory menuFactory;
  @Mock
  private CarouselService carouselService;
  @Mock
  private WebMessageSourceImpl messageSource;
  @Mock
  private NewsEntryService newsEntryService;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private PageService pageService;
  @Mock
  private WidgetPageService widgetPageService;
  @Mock
  private WidgetService widgetService;
  @Mock
  private PluginRegistry<WidgetProviderPlugin, String> widgetProviders;

  @Spy
  private FactoryConfiguration configuration;

  @Test
  public void testDisplayFactory() throws Exception {

    DisplayFactory result = configuration.displayFactory(messageSource, pageService, newsEntryService,
        widgetPageService, widgetService);

    Assert.assertEquals(DisplayFactoryImpl.class, result.getClass());
  }

}
