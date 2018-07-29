package codeu.controller;

import codeu.model.data.Action;
import codeu.model.data.Activity;
import codeu.model.store.basic.ActivityStore;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ActivityServletTest {
  private ActivityServlet activityServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private ActivityStore mockActivityStore;

  @Before
  public void setup() {
    activityServlet = new ActivityServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/activity.jsp"))
        .thenReturn(mockRequestDispatcher);

    mockActivityStore = Mockito.mock(ActivityStore.class);
    activityServlet.setActivityStore(mockActivityStore);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    List<Activity> fakeActivityList = new ArrayList<>();
    fakeActivityList.add(
        new Activity(
            UUID.randomUUID(), UUID.randomUUID(), Action.REGISTER_USER, true, Instant.now(), null));
    Mockito.when(mockActivityStore.getAllActivities()).thenReturn(fakeActivityList);

    activityServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("activities", fakeActivityList);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
}
