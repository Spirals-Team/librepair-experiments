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
 * Test Add user controller.
 */
public class AddUsersControllerTest {

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
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(req.getSession()).thenReturn(session);
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new AddUsersController().doGet(req, resp);

        verify(req, atLeastOnce()).setAttribute(anyString(), anyCollectionOf(Role.class));
        verify(req, atLeastOnce()).getRequestDispatcher("/WEB-INF/views/AddUserView.jsp");
    }

    @Test
    public void whenAdminCreatesAnotherAdminThenUserCreated()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.ADMIN));
        when(req.getParameter("name")).thenReturn("Admin");
        when(req.getParameter("login")).thenReturn("admin");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("admin@email.com");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.ADMIN));

        new AddUsersController().doPost(req, resp);

        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(1);
        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        assertThat(allUsers.size(), is(2));
        assertThat(u.getName(), is("Admin"));
        assertThat(u.getLogin(), is("admin"));
        assertThat(u.getEmail(), is("admin@email.com"));
        assertThat(u.getRole(), is(Role.ADMIN));
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenAdminCreatesUserThenUserCreated()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.ADMIN));
        when(req.getParameter("name")).thenReturn("Simple User");
        when(req.getParameter("login")).thenReturn("su");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("su@email.com");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.USER));

        new AddUsersController().doPost(req, resp);

        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(1);
        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        assertThat(allUsers.size(), is(2));
        assertThat(u.getName(), is("Simple User"));
        assertThat(u.getLogin(), is("su"));
        assertThat(u.getEmail(), is("su@email.com"));
        assertThat(u.getRole(), is(Role.USER));
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenUserCreatesAnotherUserThenUserCreated()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.USER));
        when(req.getParameter("name")).thenReturn("Simple User");
        when(req.getParameter("login")).thenReturn("su");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("su@email.com");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.USER));

        new AddUsersController().doPost(req, resp);

        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(1);
        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        assertThat(allUsers.size(), is(2));
        assertThat(u.getName(), is("Simple User"));
        assertThat(u.getLogin(), is("su"));
        assertThat(u.getEmail(), is("su@email.com"));
        assertThat(u.getRole(), is(Role.USER));
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenUserCreatesGuestThenUserCreated()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.USER));
        when(req.getParameter("name")).thenReturn("Simple User");
        when(req.getParameter("login")).thenReturn("su");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("su@email.com");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.GUEST));

        new AddUsersController().doPost(req, resp);

        List<User> allUsers = new UserDaoImpl().getAllUsers();
        User u = allUsers.get(1);
        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        assertThat(allUsers.size(), is(2));
        assertThat(u.getName(), is("Simple User"));
        assertThat(u.getLogin(), is("su"));
        assertThat(u.getEmail(), is("su@email.com"));
        assertThat(u.getRole(), is(Role.GUEST));
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenUserCreatesAdminThenErrorShown()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.USER));
        when(req.getParameter("name")).thenReturn("Admin");
        when(req.getParameter("login")).thenReturn("admin");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("admin@email.com");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.ADMIN));
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new AddUsersController().doPost(req, resp);

        List<User> allUsers = new UserDaoImpl().getAllUsers();
        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        assertThat(allUsers.size(), is(1));
        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to set role: ADMIN");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenGuestCreatesAdminThenErrorShown()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.GUEST));
        when(req.getParameter("name")).thenReturn("Admin");
        when(req.getParameter("login")).thenReturn("admin");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("admin@email.com");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.ADMIN));
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new AddUsersController().doPost(req, resp);

        List<User> allUsers = new UserDaoImpl().getAllUsers();
        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        assertThat(allUsers.size(), is(1));
        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to set role: ADMIN");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenGuestCreatesUserThenErrorShown()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.GUEST));
        when(req.getParameter("name")).thenReturn("Simple User");
        when(req.getParameter("login")).thenReturn("su");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("su@email.com");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.USER));
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new AddUsersController().doPost(req, resp);

        List<User> allUsers = new UserDaoImpl().getAllUsers();
        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        assertThat(allUsers.size(), is(1));
        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to set role: USER");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenGuestCreatesAnotherGuestThenErrorShown()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Role.GUEST));
        when(req.getParameter("name")).thenReturn("Simple User");
        when(req.getParameter("login")).thenReturn("su");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("email")).thenReturn("su@email.com");
        when(req.getParameter("role")).thenReturn(String.valueOf(Role.GUEST));
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new AddUsersController().doPost(req, resp);

        List<User> allUsers = new UserDaoImpl().getAllUsers();
        verify(req, atLeastOnce()).getParameter("name");
        verify(req, atLeastOnce()).getParameter("login");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("role");
        assertThat(allUsers.size(), is(1));
        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to set role: GUEST");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }
}