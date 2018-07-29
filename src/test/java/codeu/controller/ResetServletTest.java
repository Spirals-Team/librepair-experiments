package codeu.controller;

import codeu.model.data.ModelDataTestHelpers;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ResetServletTest {

  private ResetServlet resetServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private UserStore mockUserStore;


  @Before
  public void setup() {
    resetServlet = new ResetServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    mockUserStore = Mockito.mock(UserStore.class);
    resetServlet.setUserStore(mockUserStore);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    Mockito.when(mockRequest.getRequestURI()).thenReturn("testing/chat/reset");
    Mockito.when(mockRequest.getParameter("username")).thenReturn("user");
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/reset.jsp"))
            .thenReturn(mockRequestDispatcher);

    resetServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_NonExistingUser() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("username")).thenReturn("user");
    Mockito.when(mockRequest.getParameter("password")).thenReturn("0000");
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/reset.jsp"))
            .thenReturn(mockRequestDispatcher);

    resetServlet.doPost(mockRequest, mockResponse);

    UserStore mockUserStore = Mockito.mock(UserStore.class);
    Mockito.when(mockUserStore.isUserRegistered("user")).thenReturn(false);
    resetServlet.setUserStore(mockUserStore);

    Mockito.verify(mockRequest)
            .setAttribute("error", "Username does not exist.");
    Mockito.verify(mockRequest)
            .setAttribute("isReset", "false");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_NoEmail() throws IOException, ServletException {
    Mockito.when(mockRequest.getRequestURI()).thenReturn("testing/chat/reset");
    Mockito.when(mockRequest.getParameter("username")).thenReturn("user");
    Mockito.when(mockRequest.getParameter("password")).thenReturn("0000");
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/register.jsp"))
            .thenReturn(mockRequestDispatcher);


    UserStore mockUserStore = Mockito.mock(UserStore.class);
    User user = new ModelDataTestHelpers.TestUserBuilder().withName("user").build();
    user.setEmail("");
    Mockito.when(mockUserStore.isUserRegistered("user")).thenReturn(true);
    Mockito.when(mockUserStore.getUser("user")).thenReturn(user);

    resetServlet.setUserStore(mockUserStore);
    resetServlet.doPost(mockRequest, mockResponse);
    Mockito.verify(mockRequest)
            .setAttribute("error", "No email. Sorry, we cannot retrieve the password.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPostResetQuestion() throws IOException, ServletException {
    Mockito.when(mockRequest.getRequestURI()).thenReturn("testing/chat/reset");
    Mockito.when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/reset"));
    Mockito.when(mockRequest.getParameter("username")).thenReturn("user");
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/login.jsp"))
            .thenReturn(mockRequestDispatcher);


    UserStore mockUserStore = Mockito.mock(UserStore.class);
    User user = new ModelDataTestHelpers.TestUserBuilder().withName("name").build();
    user.setEmail("user@gmail.com");
    Mockito.when(mockUserStore.isUserRegistered("user")).thenReturn(true);
    Mockito.when(mockUserStore.getUser("user")).thenReturn(user);

    resetServlet.setUserStore(mockUserStore);

    resetServlet.doPost(mockRequest, mockResponse);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPostReset() throws IOException, ServletException {
    Mockito.when(mockRequest.getRequestURI()).thenReturn("testing/chat/reset");
    Mockito.when(mockRequest.getParameter("username")).thenReturn("user");
    Mockito.when(mockRequest.getParameter("password")).thenReturn("newPassword");

    UserStore mockUserStore = Mockito.mock(UserStore.class);
    User user = new ModelDataTestHelpers.TestUserBuilder().withName("name").build();
    user.setEmail("user@gmail.com");
    Mockito.when(mockUserStore.isUserRegistered("user")).thenReturn(true);
    Mockito.when(mockUserStore.getUser("user")).thenReturn(user);

    resetServlet.setUserStore(mockUserStore);

    resetServlet.doPost(mockRequest, mockResponse);
    Mockito.verify(mockResponse).sendRedirect("/login");
  }
}
