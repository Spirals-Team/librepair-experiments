package ru.job4j.servlets.http;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import static org.mockito.Mockito.*;

public class SigninControllerTest {
    private String login = "LoginForTest";
    private String password = "PasswordForTest";

    /**
     * Before test.
     */
    @Before
    public void before() {
        ValidateService.getInstance().add(null, login, null, null, password);
    }

    /**
     * After test.
     */
    @After
    public void after() {
        ValidateService.getInstance().delete(login);
    }

    /**
     * Test successful signin.
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Test
    public void successfulSignin() throws ServletException, IOException {
        SigninController signin = new SigninController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getSession()).thenReturn(session);

        signin.doPost(request, response);
        verify(response).sendRedirect(String.format("%s/", request.getContextPath()));
    }

    /**
     * Test unsuccessful signin.
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Test
    public void unsuccessful() throws IOException, ServletException {
        SigninController signin = new SigninController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher rd = mock(RequestDispatcher.class);

        when(request.getParameter("login")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("test");
        when(request.getRequestDispatcher("/WEB-INF/views/login.jsp")).thenReturn(rd);
        signin.doPost(request, response);
        verify(rd).forward(request, response);
    }
}