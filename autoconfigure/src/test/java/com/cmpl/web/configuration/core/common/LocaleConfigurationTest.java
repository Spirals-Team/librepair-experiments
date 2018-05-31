package com.cmpl.web.configuration.core.common;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@RunWith(MockitoJUnitRunner.class)
public class LocaleConfigurationTest {

  @Spy
  private LocaleConfiguration configuration;

  @Test
  public void testLocaleResolver() throws Exception {
    LocaleResolver result = configuration.localeResolver();

    Assert.assertEquals(SessionLocaleResolver.class, result.getClass());
    Assert.assertEquals(Locale.FRANCE, result.resolveLocale(BDDMockito.mock(HttpServletRequest.class)));
  }

}
