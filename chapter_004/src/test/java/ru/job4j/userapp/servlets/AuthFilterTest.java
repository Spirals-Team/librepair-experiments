package ru.job4j.userapp.servlets;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

/**
 * Test for AuthFilter
 */
public class AuthFilterTest {

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private HttpSession session;
    @Mock
    private FilterChain chain;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenURLContainsSigninThanFilterInvokes() throws IOException, ServletException {
        String path = "/signin";
        when(req.getRequestURI()).thenReturn(path);
        when(req.getSession()).thenReturn(session);

        new AuthFilter().doFilter(req, resp, chain);

        verify(req, atLeastOnce()).getRequestURI();
        assertThat(req.getRequestURI(), is(path));
        verify(chain, atLeastOnce()).doFilter(req, resp);
    }

    @Test
    public void whenURLIsntContainSigninThanFilterIsntInvoke() throws IOException, ServletException {
        String path = "/nonsignin";
        when(req.getRequestURI()).thenReturn(path);
        when(req.getSession()).thenReturn(session);

        new AuthFilter().doFilter(req, resp, chain);

        verify(req, atLeastOnce()).getRequestURI();
        assertThat(req.getRequestURI(), is(path));
        verify(session, atLeastOnce()).getAttribute("login");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }
}