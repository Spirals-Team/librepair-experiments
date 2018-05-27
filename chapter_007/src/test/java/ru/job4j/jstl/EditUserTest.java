package ru.job4j.jstl;

import org.junit.Test;
import ru.job4j.crudservlet.User;
import ru.job4j.servlet.UserStore;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class EditUserTest {
    @Test
    public void testEditNullUser() throws ServletException, IOException {
        EditUser editUser = new EditUser();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        editUser.doGet(req, resp);
        verify(req).getRequestDispatcher(eq("/WEB-INF/views/edit.jsp"));
    }

    @Test
    public void testEditUser() throws ServletException, IOException {
        EditUser editUser = new EditUser();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        UserStore.getInstance().addUser(new User("Test", "TestTestTest", "test@test", "user"));
        String id = UserStore.getInstance().getUser("TestTestTest").getId();
        when(req.getParameter("id")).thenReturn(id);
        when(req.getParameter("name")).thenReturn("newTest");
        when(req.getParameter("login")).thenReturn("TestTestTest");
        when(req.getParameter("email")).thenReturn("newtest@newtest");
        when(req.getParameter("role")).thenReturn("user");
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        editUser.doGet(req, resp);
        assertThat(UserStore.getInstance().getUser("TestTestTest").getEmail(), is("newtest@newtest"));
        verify(resp).sendRedirect(String.format("%s/", req.getContextPath()));
        UserStore.getInstance().delUser(id);
    }
}