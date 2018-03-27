package ru.job4j.servlets.application.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 01.02.18;
 * @version $Id$
 * @since 0.1
 */
public class AuthFilterTest {
    @Mock
    ServletRequest servletRequest;
    @Mock
    ServletResponse servletResponse;
    @Mock
    FilterChain chain;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    String url1 = "";
    String url = "/signin";
    @Mock
    HttpSession session;
    @Before
    public void start() {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void doFilterWhenPathContainsSignin() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn(url);
        new AuthFilter().doFilter(request, response, chain);
        verify(request, atLeast(1)).getRequestURI();
        assertThat(request.getRequestURI(), is(url));
        verify(chain, atLeast(1)).doFilter(request, response);
    }

    @Test
    public void doFilterWhenPathNotContainsSignin() throws Exception {
        when(request.getRequestURI()).thenReturn(url1);
        new AuthFilter().doFilter(request, response, chain);
        assertThat(request.getRequestURI(), is(url1));
        verify(request, atLeast(1)).getSession();
        verify(response, atLeast(1)).sendRedirect(anyString());
    }

    @Test
    public void doFilter() throws Exception {
        when(request.getRequestURI()).thenReturn(url1);
        when(session.getAttribute(anyString())).thenReturn(url);
        new AuthFilter().doFilter(request, response, chain);
        verify(chain, atLeast(1)).doFilter(request, response);
    }

    @Test
    public void doFilterWhenWasSelectedLogOut() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn(url1);
        when(session.getAttribute(anyString())).thenReturn(url);
        when(request.getParameter(anyString())).thenReturn("exit");
        new AuthFilter().doFilter(request, response, chain);
        verify(session, atLeast(1)).invalidate();
        verify(response, atLeast(1)).sendRedirect(anyString());
    }
}