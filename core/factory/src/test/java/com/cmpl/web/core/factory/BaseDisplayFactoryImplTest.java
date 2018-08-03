package com.cmpl.web.core.factory;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.common.message.WebMessageSourceImpl;

@RunWith(MockitoJUnitRunner.class)
public class BaseDisplayFactoryImplTest {

  @Mock
  private WebMessageSourceImpl messageSource;

  @InjectMocks
  @Spy
  private BaseDisplayFactoryImpl displayFactory;

  @Test
  public void testComputeHiddenLink() throws Exception {
    String href = "/manager/";

    String result = displayFactory.computeHiddenLink(Locale.FRANCE);

    Assert.assertEquals(href, result);

  }

}
