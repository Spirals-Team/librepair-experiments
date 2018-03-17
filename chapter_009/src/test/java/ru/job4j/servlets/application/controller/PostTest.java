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
public class PostTest {
    @Mock
    HttpServletResponse response;
    @Mock
    HttpServletRequest request;
    String string = "login";
    @Mock
    RequestDispatcher dispatcher;
    @Before
    public void start() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doPosWhenAllDateWasInserted() throws ServletException, IOException {
        when(request.getParameter("newLogin")).thenReturn(string);
        when(request.getParameter("name")).thenReturn(string);
        when(request.getParameter("email")).thenReturn(string);
        when(request.getParameter("password")).thenReturn(string);
        when(request.getParameter("select")).thenReturn(string);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        new Post().doPost(request, response);
        verify(request, atLeast(5)).getParameter(anyString());
        verify(request, atLeast(1)).setAttribute(anyString(), any());
        verify(request, atLeast(1)).getRequestDispatcher(anyString());
        verify(dispatcher, atLeast(1)).forward(request, response);
    }

    @Test
    public void doPostWhenSomeDateWasEmpty() throws ServletException, IOException {
        when(request.getParameter("newLogin")).thenReturn("");
        when(request.getParameter("name")).thenReturn(string);
        when(request.getParameter("email")).thenReturn(string);
        when(request.getParameter("password")).thenReturn(string);
        when(request.getParameter("select")).thenReturn(string);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        new Post().doPost(request, response);
        verify(request, atLeast(1)).setAttribute(anyString(), anyString());
        verify(request, atLeast(1)).getRequestDispatcher(anyString());
        verify(dispatcher, atLeast(1)).forward(request, response);
    }
}