package ru.job4j.userapp.servlets;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.job4j.userapp.DbStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test Signin user controller.
 */
public class SigninControllerTest {

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;
    private DbStore store = DbStore.getInstance();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        store.cleanDB();
        store.initDB();
        store.addRootIfRequired();
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new SigninController().doGet(req, resp);

        verify(req, atLeastOnce()).getRequestDispatcher("/WEB-INF/views/LoginView.jsp");
    }

    @Test
    public void whenValidUserSinginThenLogged()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("login")).thenReturn("root");
        when(req.getParameter("passw")).thenReturn("root");
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new SigninController().doPost(req, resp);

        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenUserWithInvalidPasswSinginThenShownError()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("login")).thenReturn("root");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new SigninController().doPost(req, resp);

        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).setAttribute("error",
            "Credentials is invalid");
        verify(req, atLeastOnce()).getRequestDispatcher("/WEB-INF/views/LoginView.jsp");
    }

    @Test
    public void whenUserWithInvalidLoginSinginThenShownError()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("login")).thenReturn("guest");
        when(req.getParameter("passw")).thenReturn("root");
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new SigninController().doPost(req, resp);

        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).setAttribute("error",
            "Credentials is invalid");
        verify(req, atLeastOnce()).getRequestDispatcher("/WEB-INF/views/LoginView.jsp");
    }
}