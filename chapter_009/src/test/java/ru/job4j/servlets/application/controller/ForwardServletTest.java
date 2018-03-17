package ru.job4j.servlets.application.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 01.02.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class ForwardServletTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher dispatcher;
    String login = "login";
    @Before
    public void start() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doPostWhenWasSelectedUpdate() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("update");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
       new ForwardServlet().doPost(request, response);

       verify(request, atLeast(1)).getParameter(anyString());
       verify(request, atLeast(4)).setAttribute(anyString(), any());
       verify(dispatcher, atLeast(1)).forward(request, response);
    }

    @Test
    public void doPostWhenWasSelectedDelete() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        new ForwardServlet().doPost(request, response);

        verify(request, atLeast(2)).setAttribute(anyString(), any());
        verify(request, atLeast(2)).getParameter(anyString());
        verify(request, atLeast(1)).getRequestDispatcher(anyString());
        verify(dispatcher, atLeast(1)).forward(request, response);
    }

    @Test
    public void doPostWhenWasSelectedNew() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("add new user");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        new ForwardServlet().doPost(request, response);

        verify(request, atLeast(3)).setAttribute(anyString(), any());
        verify(request, atLeast(1)).getRequestDispatcher(anyString());
        verify(dispatcher, atLeast(1)).forward(request, response);

    }
}