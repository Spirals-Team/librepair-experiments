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
public class PutTest {
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    private  String login = "login";
    @Mock
    RequestDispatcher dispatcher;
    @Before
    public void start() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doPostWhenAllParamWasInserted() throws ServletException, IOException {
        when(request.getParameter("newLogin")).thenReturn(login);
        when(request.getParameter("name")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(login);
        when(request.getParameter("email")).thenReturn(login);
        when(request.getParameter("oldLogin")).thenReturn(login);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        new Put().doPost(request, response);
        verify(request, atLeast(1)).setAttribute(anyString(), any());
        verify(request, atLeast(1)).getRequestDispatcher(anyString());
        verify(dispatcher, atLeast(1)).forward(request, response);
    }

    @Test
    public void doPostWhenDateHasEmptyLine() throws ServletException, IOException {
        when(request.getParameter("newLogin")).thenReturn("");
        when(request.getParameter("name")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(login);
        when(request.getParameter("email")).thenReturn(login);
        when(request.getParameter("oldLogin")).thenReturn(login);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        new Put().doPost(request, response);
        verify(request, atLeast(2)).setAttribute(anyString(), any());
        verify(request, atLeast(1)).getRequestDispatcher(anyString());
        verify(dispatcher, atLeast(1)).forward(request, response);
    }
}