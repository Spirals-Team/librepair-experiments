package ru.job4j.jstl;

import org.junit.Test;
import ru.job4j.servlet.UserStore;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
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
public class AddUserTest {
    @Test
    public void whenAddNull() throws ServletException, IOException {
        AddUser addUser = new AddUser();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        addUser.doGet(req, resp);
        verify(req).getRequestDispatcher(eq("/WEB-INF/views/add.jsp"));
    }

    @Test
    public void whenAddDublicatLogin() throws ServletException, IOException {
        AddUser addUser = new AddUser();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("name")).thenReturn("Test");
        when(req.getParameter("login")).thenReturn("root");
        when(req.getParameter("email")).thenReturn("test@test");
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        addUser.doGet(req, resp);
        verify(req).getRequestDispatcher(eq("/WEB-INF/views/add.jsp"));
    }

    @Test
    public void whenAddnewUser() throws ServletException, IOException {
        AddUser addUser = new AddUser();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("name")).thenReturn("Test");
        when(req.getParameter("login")).thenReturn("TestTestTest");
        when(req.getParameter("email")).thenReturn("test@test");
        addUser.doGet(req, resp);
        assertThat(UserStore.getInstance().getUser("TestTestTest").getLogin(), is("TestTestTest"));
        UserStore.getInstance().delUser(UserStore.getInstance().getUser("TestTestTest").getId());
    }
}