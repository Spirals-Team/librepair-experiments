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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 01.02.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class SignControllerTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private   RequestDispatcher dispatcher;
    @Mock
    private HttpSession session;
    private   String login = "root";
    private String password = "r";
    @Before
    public void start() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        new SignController().doGet(request, response);
        verify(request, atLeast(1)).getRequestDispatcher(anyString());
        verify(dispatcher, atLeast(1)).forward(request, response);
    }

    @Test
    public void doPost() throws ServletException, IOException {
        when(request.getParameter("login")).thenReturn(login);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getSession()).thenReturn(session);
        new SignController().doPost(request, response);
        verify(request, atLeast(1)).setAttribute(anyString(), any());
        verify(request, atLeast(1)).getRequestDispatcher(anyString());
        verify(dispatcher, atLeast(1)).forward(request, response);
    }
}