package ru.job4j.userapp.servlets;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.job4j.userapp.DbStore;
import ru.job4j.userapp.Role;
import ru.job4j.userapp.User;
import ru.job4j.userapp.UserDaoImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test Remove user controller.
 */
public class RemoveUsersControllerTest {

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;
    private DbStore store = DbStore.getInstance();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        store.cleanDB();
        store.initDB();
        store.addRootIfRequired();
        User admin = new User("Admin", "admin", "1234", "admin@email.com", Role.ADMIN);
        User user = new User("User", "user", "1234", "user@email.com", Role.USER);
        User guest = new User("Guest", "guest", "1234", "guest@email.com", Role.GUEST);
        new UserDaoImpl().insertUser(admin);
        new UserDaoImpl().insertUser(user);
        new UserDaoImpl().insertUser(guest);

    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new RemoveUsersController().doGet(req, resp);

        verify(req, atLeastOnce()).setAttribute(anyString(), anyCollectionOf(Role.class));
        verify(req, atLeastOnce()).getRequestDispatcher("/WEB-INF/views/UsersView.jsp");
    }

    @Test
    public void whenAdminRemovesAnotherAdminThenAdminRemoved()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.ADMIN));
        when(req.getParameter("id")).thenReturn("2");

        new RemoveUsersController().doPost(req, resp);

        verify(req, atLeastOnce()).getParameter("id");
        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(1);
        assertThat(allUsers.size(), is(3));
        assertThat(u.getName(), not("Admin"));
        assertThat(u.getRole(), not(Role.ADMIN));
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenAdminRemovesUserThenUserUpdated()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.ADMIN));
        when(req.getParameter("id")).thenReturn("3");

        new RemoveUsersController().doPost(req, resp);

        verify(req, atLeastOnce()).getParameter("id");
        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(2);
        assertThat(allUsers.size(), is(3));
        assertThat(u.getName(), not("User"));
        assertThat(u.getRole(), not(Role.USER));
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenUserRemovesAnotherUserThenErrorShown()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.USER));
        when(req.getParameter("id")).thenReturn("3");
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new RemoveUsersController().doPost(req, resp);

        verify(req, atLeastOnce()).getParameter("id");
        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(2);
        assertThat(allUsers.size(), is(4));
        assertThat(u.getName(), is("User"));
        assertThat(u.getRole(), is(Role.USER));

        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to remove user");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenGuestRemovesUserThenErrorShown()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.GUEST));
        when(req.getParameter("id")).thenReturn("3");
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new RemoveUsersController().doPost(req, resp);

        verify(req, atLeastOnce()).getParameter("id");
        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(2);
        assertThat(allUsers.size(), is(4));
        assertThat(u.getName(), is("User"));
        assertThat(u.getRole(), is(Role.USER));

        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to remove user");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }
}