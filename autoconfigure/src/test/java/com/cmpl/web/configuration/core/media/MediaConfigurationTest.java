package com.cmpl.web.configuration.core.media;

import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.plugin.core.PluginRegistry;

import com.cmpl.web.core.breadcrumb.BreadCrumb;
import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSourceImpl;
import com.cmpl.web.core.factory.media.MediaManagerDisplayFactoryImpl;
import com.cmpl.web.core.factory.menu.MenuFactory;
import com.cmpl.web.core.file.FileService;
import com.cmpl.web.core.media.MediaRepository;
import com.cmpl.web.core.media.MediaService;
import com.cmpl.web.core.media.MediaServiceImpl;
import com.cmpl.web.core.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class MediaConfigurationTest {

  @Spy
  private MediaConfiguration configuration;

  @Mock
  private MediaRepository mediaRepository;
  @Mock
  private MenuFactory menuFactory;
  @Mock
  private WebMessageSourceImpl messageSource;
  @Mock
  private MediaService mediaService;
  @Mock
  private FileService fileService;
  @Mock
  private ContextHolder contextHolder;
  @Mock
  private PluginRegistry<BreadCrumb, BACK_PAGE> breadCrumbRegistry;
  @Mock
  private Set<Locale> availableLocales;

  @Mock
  private ApplicationEventPublisher publisher;

  @Test
  public void testMediaService() throws Exception {
    Assert.assertEquals(MediaServiceImpl.class,
        configuration.mediaService(publisher, mediaRepository, fileService).getClass());
  }

  @Test
  public void testMediaManagerDisplayFactory() throws Exception {
    Assert.assertEquals(MediaManagerDisplayFactoryImpl.class, configuration.mediaManagerDisplayFactory(menuFactory,
        messageSource, mediaService, contextHolder, breadCrumbRegistry, availableLocales).getClass());
  }

}
