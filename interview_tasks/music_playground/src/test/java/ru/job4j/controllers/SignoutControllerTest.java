package ru.job4j.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.job4j.DbConnector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test Signout user controller.
 */
public class SignoutControllerTest {

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        DbConnector connector = DbConnector.getInstance();
        connector.cleanDB();
        connector.initDB();
    }

    @Test
    public void whenValidUserSingoutThenLogout()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);

        new SignoutController().doPost(req, resp);

        verify(session, atLeastOnce()).invalidate();
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }
}