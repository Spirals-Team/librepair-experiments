package ru.job4j.servlets.application.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 01.02.18;
 * @version $Id$
 * @since 0.1
 */
public class UsersControllerTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;
    private String login = "root";
    @Before
    public void setUp() throws ServletException, IOException {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("login")).thenReturn(login);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

    }

    @Test
    public void doGet() throws ServletException, IOException {
        new UsersController().doGet(request, response);
        verify(request, atLeast(1)).getSession();
        verify(session, atLeast(1)).getAttribute("login");
        verify(request, atLeast(1)).setAttribute(anyString(), any());

    }

}