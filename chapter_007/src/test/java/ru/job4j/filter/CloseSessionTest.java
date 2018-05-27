package ru.job4j.filter;

import org.junit.Test;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class CloseSessionTest {
    @Test
    public void closeSession() throws ServletException, IOException {
        CloseSession closeSession = new CloseSession();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        closeSession.doPost(req, resp);
        verify(session).invalidate();
    }
}