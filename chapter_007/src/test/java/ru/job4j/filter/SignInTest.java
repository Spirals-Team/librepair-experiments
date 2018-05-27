package ru.job4j.filter;

import org.junit.Test;
import org.junit.runner.Request;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SignInTest {
    @Test
    public void testSignInLoginTrue() throws ServletException, IOException {
        SignIn signIn = new SignIn();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("login")).thenReturn("root");
        when(req.getParameter("email")).thenReturn("root@root");
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        signIn.doPost(req, resp);
        verify(resp).sendRedirect(String.format("%s/", req.getContextPath()));
    }

    @Test
    public void testSignInLoginFalse() throws ServletException, IOException {
        SignIn signIn = new SignIn();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("login")).thenReturn("");
        when(req.getParameter("email")).thenReturn("");
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        signIn.doPost(req, resp);
        verify(req).getRequestDispatcher(eq("/WEB-INF/views/Login.jsp"));
    }
}