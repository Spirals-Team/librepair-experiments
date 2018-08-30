package ru.job4j.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.job4j.DbConnector;
import ru.job4j.UserRepository;
import ru.job4j.model.MusicType;
import ru.job4j.model.Role;
import ru.job4j.model.Roles;
import ru.job4j.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
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

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        DbConnector connector = DbConnector.getInstance();
        connector.cleanDB();
        connector.initDB();
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
        when(session.getAttribute("role")).thenReturn(String.valueOf(Roles.ADMIN));
        when(req.getParameter("email")).thenReturn("admin1@email.com");
        when(req.getParameter("passw")).thenReturn("admin1234");
        when(req.getParameter("role")).thenReturn(String.valueOf(Roles.ADMIN));
        when(req.getParameter("country")).thenReturn("Belgium");
        when(req.getParameter("city")).thenReturn("Ghent");
        when(req.getParameter("street_address")).thenReturn("Jakobijnenstraat 6");
        when(req.getParameterValues("genres")).thenReturn(new String[]{"RAP", "FUNK"});

        new AddUsersController().doPost(req, resp);

        List<User> allUsers = new UserRepository().findAll();
        User u = allUsers.get(1);
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("role");
        verify(req, atLeastOnce()).getParameter("country");
        verify(req, atLeastOnce()).getParameter("city");
        verify(req, atLeastOnce()).getParameter("street_address");
        verify(req, atLeastOnce()).getParameterValues("genres");
        assertThat(allUsers.size(), is(2));
        assertThat(u.getEmail(), is("admin1@email.com"));
        assertThat(u.getRole().getType(), is(Roles.ADMIN.name()));
        assertThat(u.getGenres(), hasSize(2));
        assertThat(u.getGenres().stream().map(MusicType::getGenre)
            .collect(Collectors.toList()), containsInAnyOrder("RAP", "FUNK"));
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenAdminCreatesUserThenUserCreated()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Roles.ADMIN));
        when(req.getParameter("email")).thenReturn("su@email.com");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("country")).thenReturn("Greece");
        when(req.getParameter("role")).thenReturn(String.valueOf(Roles.USER));
        when(req.getParameterValues("genres")).thenReturn(new String[]{"RAP", "FUNK"});

        new AddUsersController().doPost(req, resp);

        List<User> allUsers = new UserRepository().findAll();
        User u = allUsers.get(1);
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("role");
        verify(req, atLeastOnce()).getParameter("country");
        verify(req, atLeastOnce()).getParameterValues("genres");
        assertThat(allUsers.size(), is(2));
        assertThat(u.getEmail(), is("su@email.com"));
        assertThat(u.getRole().getType(), is(Roles.USER.name()));
        assertThat(u.getGenres(), hasSize(2));
        assertThat(u.getGenres().stream().map(MusicType::getGenre)
            .collect(Collectors.toList()), containsInAnyOrder("RAP", "FUNK"));
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenMandatorCreatesAnotherMandatorThenMandatorCreated()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Roles.MANDATOR));
        when(req.getParameter("email")).thenReturn("su@email.com");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("country")).thenReturn("Greece");
        when(req.getParameter("role")).thenReturn(String.valueOf(Roles.MANDATOR));
        when(req.getParameterValues("genres")).thenReturn(new String[]{"RAP", "FUNK"});

        new AddUsersController().doPost(req, resp);

        List<User> allUsers = new UserRepository().findAll();
        User u = allUsers.get(1);
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("role");
        verify(req, atLeastOnce()).getParameter("country");
        verify(req, atLeastOnce()).getParameterValues("genres");
        assertThat(allUsers.size(), is(2));
        assertThat(u.getEmail(), is("su@email.com"));
        assertThat(u.getRole().getType(), is(Roles.MANDATOR.name()));
        assertThat(u.getGenres(), hasSize(2));
        assertThat(u.getGenres().stream().map(MusicType::getGenre)
            .collect(Collectors.toList()), containsInAnyOrder("RAP", "FUNK"));
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenMandatorCreatesUserThenUserCreated()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Roles.MANDATOR));
        when(req.getParameter("email")).thenReturn("su@email.com");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("country")).thenReturn("Greece");
        when(req.getParameter("role")).thenReturn(String.valueOf(Roles.USER));
        when(req.getParameterValues("genres")).thenReturn(new String[]{"RAP", "FUNK"});

        new AddUsersController().doPost(req, resp);

        List<User> allUsers = new UserRepository().findAll();
        User u = allUsers.get(1);
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("role");
        verify(req, atLeastOnce()).getParameter("country");
        verify(req, atLeastOnce()).getParameterValues("genres");
        assertThat(allUsers.size(), is(2));
        assertThat(u.getEmail(), is("su@email.com"));
        assertThat(u.getRole().getType(), is(Roles.USER.name()));
        assertThat(u.getGenres(), hasSize(2));
        assertThat(u.getGenres().stream().map(MusicType::getGenre)
            .collect(Collectors.toList()), containsInAnyOrder("RAP", "FUNK"));
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenMandatorCreatesAdminThenErrorShown()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Roles.MANDATOR));
        when(req.getParameter("email")).thenReturn("admin1@email.com");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("country")).thenReturn("Greece");
        when(req.getParameter("role")).thenReturn(String.valueOf(Roles.ADMIN));
        when(req.getParameterValues("genres")).thenReturn(new String[]{"RAP", "FUNK"});
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new AddUsersController().doPost(req, resp);

        List<User> allUsers = new UserRepository().findAll();
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("role");
        verify(req, atLeastOnce()).getParameter("country");
        verify(req, atLeastOnce()).getParameterValues("genres");
        assertThat(allUsers.size(), is(1));
        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to set role: ADMIN");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenUserCreatesAdminThenErrorShown()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Roles.USER));
        when(req.getParameter("email")).thenReturn("admin1@email.com");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("country")).thenReturn("Greece");
        when(req.getParameter("role")).thenReturn(String.valueOf(Roles.ADMIN));
        when(req.getParameterValues("genres")).thenReturn(new String[]{"RAP", "FUNK"});
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new AddUsersController().doPost(req, resp);

        List<User> allUsers = new UserRepository().findAll();
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("role");
        verify(req, atLeastOnce()).getParameter("country");
        verify(req, atLeastOnce()).getParameterValues("genres");
        assertThat(allUsers.size(), is(1));
        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to set role: ADMIN");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenUserCreatesMandatorThenErrorShown()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Roles.USER));
        when(req.getParameter("email")).thenReturn("su@email.com");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("country")).thenReturn("Greece");
        when(req.getParameterValues("genres")).thenReturn(new String[]{"RAP", "FUNK"});
        when(req.getParameter("role")).thenReturn(String.valueOf(Roles.MANDATOR));
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new AddUsersController().doPost(req, resp);

        List<User> allUsers = new UserRepository().findAll();
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("role");
        verify(req, atLeastOnce()).getParameter("country");
        verify(req, atLeastOnce()).getParameterValues("genres");
        assertThat(allUsers.size(), is(1));
        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to set role: MANDATOR");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenUserCreatesAnotherUserThenErrorShown()
        throws IOException, ServletException {
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Roles.USER));
        when(req.getParameter("email")).thenReturn("su@email.com");
        when(req.getParameter("passw")).thenReturn("1234");
        when(req.getParameter("country")).thenReturn("Greece");
        when(req.getParameterValues("genres")).thenReturn(new String[]{"RAP", "FUNK"});
        when(req.getParameter("role")).thenReturn(String.valueOf(Roles.USER));
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new AddUsersController().doPost(req, resp);

        List<User> allUsers = new UserRepository().findAll();
        verify(req, atLeastOnce()).getParameter("email");
        verify(req, atLeastOnce()).getParameter("passw");
        verify(req, atLeastOnce()).getParameter("role");
        verify(req, atLeastOnce()).getParameter("country");
        verify(req, atLeastOnce()).getParameterValues("genres");
        assertThat(allUsers.size(), is(1));
        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to set role: USER");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }
}