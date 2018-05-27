package ru.job4j.jstl;

import org.junit.Test;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class UserControllerTest {
    @Test
    public void testUserController() throws ServletException, IOException {
        UserController userController = new UserController();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        userController.doGet(req, resp);
        verify(req).getRequestDispatcher(eq("/WEB-INF/views/UsersView.jsp"));
    }
}