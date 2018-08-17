package ru.job4j.servlets.http;

import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExitServletTest {
    /**
     * Test exit.
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Test
    public void exit() throws ServletException, IOException {
        ExitServlet exit = new ExitServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        exit.doPost(request, response);
        verify(session).invalidate();
    }
}