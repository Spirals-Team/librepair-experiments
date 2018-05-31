package com.cmpl.web.configuration.modules.facebook;

import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSourceImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.news.NewsEntryService;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.facebook.FacebookAdapter;
import com.cmpl.web.facebook.FacebookDispatcher;
import com.cmpl.web.facebook.FacebookDispatcherImpl;
import com.cmpl.web.facebook.FacebookImportService;
import com.cmpl.web.facebook.FacebookImportServiceImpl;
import com.cmpl.web.facebook.FacebookImportTranslator;
import com.cmpl.web.facebook.FacebookImportTranslatorImpl;
import com.cmpl.web.facebook.FacebookService;
import com.cmpl.web.facebook.FacebookServiceImpl;
import com.cmpl.web.modules.facebook.factory.FacebookDisplayFactory;
import com.cmpl.web.modules.facebook.factory.FacebookDisplayFactoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class FacebookConfigurationTest {

  @Mock
  FacebookImportService facebookImportService;

  @Mock
  FacebookImportTranslator facebookImportTranslator;

  @Mock
  private ContextHolder contextHolder;

  @Mock
  private MenuFactory menuFactory;

  @Mock
  private WebMessageSourceImpl messageSource;

  @Mock
  private Facebook facebookConnector;

  @Mock
  private NewsEntryService newsEntryService;

  @Mock
  private FacebookAdapter facebookAdapter;

  @Mock
  private MediaService mediaService;

  @Mock
  private FileService fileService;

  @Mock
  private ConnectionRepository connectionRepository;

  @Mock
  private PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry;
  @Mock
  private Set<Locale> availableLocales;

  @Spy
  private FacebookConfiguration configuration;

  @Test
  public void testFacebookDispatcher() throws Exception {

    FacebookDispatcher result = configuration.facebookDispatcher(facebookImportService, facebookImportTranslator);

    Assert.assertEquals(FacebookDispatcherImpl.class, result.getClass());

  }

  @Test
  public void testFacebookImportTranslator() throws Exception {

    FacebookImportTranslator result = configuration.facebookImportTranslator();

    Assert.assertEquals(FacebookImportTranslatorImpl.class, result.getClass());
  }

  @Test
  public void testFacebookDisplayFactory() throws Exception {
    FacebookDisplayFactory result = configuration.facebookDisplayFactory(menuFactory, messageSource, facebookAdapter,
        breadCrumbRegistry, availableLocales);

    Assert.assertEquals(FacebookDisplayFactoryImpl.class, result.getClass());
  }

  @Test
  public void testFacebookService() throws Exception {
    FacebookService result = configuration.facebookService(contextHolder, facebookConnector, connectionRepository,
        newsEntryService);

    Assert.assertEquals(FacebookServiceImpl.class, result.getClass());
  }

  @Test
  public void testFacebookImportService() throws Exception {

    FacebookImportService result = configuration.facebookImportService(newsEntryService, facebookAdapter, mediaService,
        fileService, messageSource);

    Assert.assertEquals(FacebookImportServiceImpl.class, result.getClass());
  }
}
