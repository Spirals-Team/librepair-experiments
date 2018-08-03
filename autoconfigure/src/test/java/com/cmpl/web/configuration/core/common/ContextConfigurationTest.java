package com.cmpl.web.configuration.core.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.common.context.ContextHolder;

@RunWith(MockitoJUnitRunner.class)
public class ContextConfigurationTest {

  @Spy
  private ContextConfiguration configuration;

  @Test
  public void testContextHolder() throws Exception {

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy");

    ContextHolder holder = configuration.contextHolder();

    LocalDate dateToFormat = LocalDate.now();
    Assert.assertEquals(dateFormat.format(dateToFormat), holder.getDateFormat().format(dateToFormat));

  }
}
