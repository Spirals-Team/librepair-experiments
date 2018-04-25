package codeu.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mindrot.jbcrypt.*;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;

public class RegisterServletTest {

 private RegisterServlet registerServlet;
 private HttpServletRequest mockRequest;
 private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private static final String USERNAME = "test_username";
  private static final String PASSWORD = "test_password";

 @Before
 public void setup() {
   registerServlet = new RegisterServlet();
   mockRequest = Mockito.mock(HttpServletRequest.class);
   mockResponse = Mockito.mock(HttpServletResponse.class);
   mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
   Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/register.jsp"))
       .thenReturn(mockRequestDispatcher);
 }

 @Test
 public void testDoGet() throws IOException, ServletException {
   registerServlet.doGet(mockRequest, mockResponse);

   Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
 }
 
 @Test
 public void testDoPost_NewUser() throws IOException, ServletException {
   Mockito.when(mockRequest.getParameter("username")).thenReturn(USERNAME);
   Mockito.when(mockRequest.getParameter("password")).thenReturn(PASSWORD);
   
   UserStore mockUserStore = Mockito.mock(UserStore.class);
    
   Mockito.when(mockUserStore.isUserRegistered(USERNAME)).thenReturn(false);
   registerServlet.setUserStore(mockUserStore);

   HttpSession mockSession = Mockito.mock(HttpSession.class);
   Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

   registerServlet.doPost(mockRequest, mockResponse);


   ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

   Mockito.verify(mockUserStore).addUser(userArgumentCaptor.capture());
   Assert.assertEquals(userArgumentCaptor.getValue().getName(), USERNAME);
   Assert.assertTrue(BCrypt.checkpw(PASSWORD, userArgumentCaptor.getValue().getPassword()));

   Mockito.verify(mockResponse).sendRedirect("/login");
 }
}
