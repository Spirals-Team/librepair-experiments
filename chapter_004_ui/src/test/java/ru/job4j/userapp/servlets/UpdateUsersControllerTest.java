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
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test Update user controller.
 */
public class UpdateUsersControllerTest {

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
        User admin = new User("Admin", "admin", "1234", "admin@email.com", "Europe", "Greece", Role.ADMIN);
        User user = new User("User", "user", "1234", "user@email.com", "Europe", "Greece", Role.USER);
        User guest = new User("Guest", "guest", "1234", "guest@email.com", "Europe", "Greece", Role.GUEST);
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
    public void whenAdminUpdatesAnotherAdminThenUserUpdated()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.ADMIN));
        when(req.getParameter("id")).thenReturn("2");
        when(req.getParameter("name")).thenReturn("Admin2");
        when(req.getParameter("login")).thenReturn("admin");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("admin@email.com");
        when(req.getParameter("region")).thenReturn("Europe");
        when(req.getParameter("country")).thenReturn("Greece");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.ADMIN));

        new UpdateUsersController().doPost(req, resp);

        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(1);
        assertThat(allUsers.size(), is(4));
        assertThat(u.getName(), is("Admin2"));
        assertThat(u.getLogin(), is("admin"));
        assertThat(u.getEmail(), is("admin@email.com"));
        assertThat(u.getRole(), is(Role.ADMIN));
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenAdminUpdatesUserThenUserUpdated()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.ADMIN));
        when(req.getParameter("id")).thenReturn("3");
        when(req.getParameter("name")).thenReturn("User");
        when(req.getParameter("login")).thenReturn("su");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("su@email.com");
        when(req.getParameter("region")).thenReturn("Europe");
        when(req.getParameter("country")).thenReturn("Greece");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.USER));

        new UpdateUsersController().doPost(req, resp);

        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(2);
        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        assertThat(allUsers.size(), is(4));
        assertThat(u.getName(), is("User"));
        assertThat(u.getLogin(), is("su"));
        assertThat(u.getEmail(), is("su@email.com"));
        assertThat(u.getRole(), is(Role.USER));
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenUserUpdatesAnotherUserThenErrorShown()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.USER));
        when(session.getAttribute("login")).thenReturn("login");
        when(req.getParameter("id")).thenReturn("3");
        when(req.getParameter("name")).thenReturn("User");
        when(req.getParameter("login")).thenReturn("su2");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("su@email.com");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.USER));
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new UpdateUsersController().doPost(req, resp);

        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(2);
        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        assertThat(allUsers.size(), is(4));
        assertThat(u.getName(), is("User"));
        assertThat(u.getLogin(), is("user"));
        assertThat(u.getEmail(), is("user@email.com"));
        assertThat(u.getRole(), is(Role.USER));

        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to edit user");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenUserUpdatesHimselfThenHeUpdated()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.USER));
        when(session.getAttribute("login")).thenReturn("user");
        when(req.getParameter("id")).thenReturn("3");
        when(req.getParameter("name")).thenReturn("User");
        when(req.getParameter("login")).thenReturn("user");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("su@email.com");
        when(req.getParameter("region")).thenReturn("Europe");
        when(req.getParameter("country")).thenReturn("Greece");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.USER));

        new UpdateUsersController().doPost(req, resp);

        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(2);
        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        assertThat(allUsers.size(), is(4));
        assertThat(u.getName(), is("User"));
        assertThat(u.getLogin(), is("user"));
        assertThat(u.getEmail(), is("su@email.com"));
        assertThat(u.getRole(), is(Role.USER));
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenUserUpdatesGuestThenErrorShown()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.USER));
        when(session.getAttribute("login")).thenReturn("login");
        when(req.getParameter("id")).thenReturn("4");
        when(req.getParameter("name")).thenReturn("Guest");
        when(req.getParameter("login")).thenReturn("guest");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("gu@email.com");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.GUEST));
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new UpdateUsersController().doPost(req, resp);

        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(3);
        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        assertThat(allUsers.size(), is(4));
        assertThat(u.getName(), is("Guest"));
        assertThat(u.getLogin(), is("guest"));
        assertThat(u.getEmail(), is("guest@email.com"));
        assertThat(u.getRole(), is(Role.GUEST));

        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to edit user");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenGuestUpdatesAnotherGuestThenErrorShown()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.GUEST));
        when(session.getAttribute("login")).thenReturn("login");
        when(req.getParameter("id")).thenReturn("4");
        when(req.getParameter("name")).thenReturn("Guest");
        when(req.getParameter("login")).thenReturn("guest");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("gu@email.com");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.GUEST));
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new UpdateUsersController().doPost(req, resp);

        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(3);
        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        assertThat(allUsers.size(), is(4));
        assertThat(u.getName(), is("Guest"));
        assertThat(u.getLogin(), is("guest"));
        assertThat(u.getEmail(), is("guest@email.com"));
        assertThat(u.getRole(), is(Role.GUEST));

        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to edit user");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenGuestUpdatesHimselfThenErrorShown()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.GUEST));
        when(session.getAttribute("login")).thenReturn("guest");
        when(req.getParameter("id")).thenReturn("4");
        when(req.getParameter("name")).thenReturn("Guest");
        when(req.getParameter("login")).thenReturn("guest");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("gu@email.com");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.GUEST));
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new UpdateUsersController().doPost(req, resp);

        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(3);
        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        assertThat(allUsers.size(), is(4));
        assertThat(u.getName(), is("Guest"));
        assertThat(u.getLogin(), is("guest"));
        assertThat(u.getEmail(), is("guest@email.com"));
        assertThat(u.getRole(), is(Role.GUEST));

        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to edit user");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenGuestUpdatesUserThenErrorShown()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.GUEST));
        when(session.getAttribute("login")).thenReturn("guest");
        when(req.getParameter("id")).thenReturn("3");
        when(req.getParameter("name")).thenReturn("User");
        when(req.getParameter("login")).thenReturn("su2");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("su@email.com");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.USER));
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new UpdateUsersController().doPost(req, resp);

        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(2);
        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        assertThat(allUsers.size(), is(4));
        assertThat(u.getName(), is("User"));
        assertThat(u.getLogin(), is("user"));
        assertThat(u.getEmail(), is("user@email.com"));
        assertThat(u.getRole(), is(Role.USER));

        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to edit user");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenGuestUpdatesAdminThenErrorShown()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.GUEST));
        when(session.getAttribute("login")).thenReturn("guest");
        when(req.getParameter("id")).thenReturn("2");
        when(req.getParameter("name")).thenReturn("Admin2");
        when(req.getParameter("login")).thenReturn("admin");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("admin@email.com");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.ADMIN));
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new UpdateUsersController().doPost(req, resp);

        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(1);
        assertThat(allUsers.size(), is(4));
        assertThat(u.getName(), is("Admin"));
        assertThat(u.getLogin(), is("admin"));
        assertThat(u.getEmail(), is("admin@email.com"));
        assertThat(u.getRole(), is(Role.ADMIN));

        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to edit user");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }
}