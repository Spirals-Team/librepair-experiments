package com.cmpl.web.manager.ui.core.login;

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

import com.cmpl.web.core.factory.login.LoginDisplayFactory;
import com.cmpl.web.core.page.BACK_PAGE;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

  @Mock
  private LoginDisplayFactory displayFactory;

  @Spy
  @InjectMocks
  private LoginController controller;

  @Test
  public void testPrintLogin() throws Exception {

    ModelAndView loginView = new ModelAndView("back/login");
    BDDMockito.doReturn(loginView).when(displayFactory)
        .computeModelAndViewForBackPage(BDDMockito.eq(BACK_PAGE.LOGIN), BDDMockito.eq(Locale.FRANCE));

    ModelAndView result = controller.printLogin();

    Assert.assertEquals(loginView, result);

    BDDMockito.verify(displayFactory, BDDMockito.times(1)).computeModelAndViewForBackPage(
        BDDMockito.eq(BACK_PAGE.LOGIN), BDDMockito.eq(Locale.FRANCE));
  }
}
