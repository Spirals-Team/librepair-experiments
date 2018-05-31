package com.cmpl.web.front.ui.index;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.DisplayFactory;

@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {

  @Mock
  private DisplayFactory displayFactory;

  @Spy
  @InjectMocks
  private IndexController controller;

  @Test
  public void testPrintIndex() throws Exception {
    ModelAndView view = new ModelAndView("pages/accueil");

    BDDMockito.given(
        displayFactory.computeModelAndViewForPage(BDDMockito.anyString(), BDDMockito.eq(Locale.FRANCE),
            BDDMockito.anyInt())).willReturn(view);

    ModelAndView result = controller.printIndex(Locale.FRANCE);

    Assert.assertEquals(view, result);

    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeModelAndViewForPage(BDDMockito.anyString(),
        BDDMockito.eq(Locale.FRANCE), BDDMockito.anyInt());
  }
}
