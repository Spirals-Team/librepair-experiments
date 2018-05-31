package com.cmpl.web.configuration.core.sitemap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.common.context.ContextHolder;
import com.cmpl.web.core.common.message.WebMessageSource;
import com.cmpl.web.core.menu.MenuService;
import com.cmpl.web.core.sitemap.SitemapService;
import com.cmpl.web.core.sitemap.SitemapServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class SitemapConfigurationTest {

  @Mock
  private WebMessageSource messageSource;

  @Mock
  private MenuService menuService;

  @Mock
  private ContextHolder contextHolder;

  @Spy
  SitemapConfiguration configuration;

  @Test
  public void testSitemapService() throws Exception {
    SitemapService result = configuration.sitemapService(menuService, messageSource, contextHolder);

    Assert.assertEquals(SitemapServiceImpl.class, result.getClass());
  }

}
