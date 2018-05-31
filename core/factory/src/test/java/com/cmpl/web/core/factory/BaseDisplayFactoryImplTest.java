package com.cmpl.web.core.factory;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.cmpl.web.core.common.message.WebMessageSourceImpl;
import com.cmpl.web.core.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class BaseDisplayFactoryImplTest {

  @Mock
  private WebMessageSourceImpl messageSource;

  @InjectMocks
  @Spy
  private BaseDisplayFactoryImpl displayFactory;

  @Test
  public void testComputeTileName() throws Exception {
    String tile = "login";

    BDDMockito.doReturn(tile).when(displayFactory).getI18nValue(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE));

    String result = displayFactory.computeTileName(BACK_PAGE.LOGIN.getTile(), Locale.FRANCE);
    Assert.assertEquals(tile, result);
  }

  @Test
  public void testComputeHiddenLink() throws Exception {
    String href = "/";
    BDDMockito.doReturn(href).when(displayFactory)
        .getI18nValue(BDDMockito.eq("back.index.href"), BDDMockito.eq(Locale.FRANCE));

    String result = displayFactory.computeHiddenLink(Locale.FRANCE);

    Assert.assertEquals(href, result);

  }

  @Test
  public void testComputeDecoratorBack() throws Exception {
    String decoratorBack = "decorator_back";
    BDDMockito.doReturn(decoratorBack).when(displayFactory)
        .getI18nValue(BDDMockito.eq("decorator.back"), BDDMockito.eq(Locale.FRANCE));

    String result = displayFactory.computeDecoratorBackTileName(Locale.FRANCE);

    Assert.assertEquals(decoratorBack, result);

  }

}
