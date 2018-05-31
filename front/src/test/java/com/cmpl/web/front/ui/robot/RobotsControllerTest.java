package com.cmpl.web.front.ui.robot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;

import com.cmpl.web.core.common.context.ContextHolder;

@RunWith(MockitoJUnitRunner.class)
public class RobotsControllerTest {

  @Mock
  ContextHolder contentHolder;

  @Spy
  @InjectMocks
  private RobotsController controller;

  @Test
  public void testPrintRobot_Ok() throws Exception {
    MockHttpServletResponse response = new MockHttpServletResponse();
    controller.printRobot(response);
  }

}
