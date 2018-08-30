package ru.job4j.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.job4j.DbConnector;
import ru.job4j.UserRepository;
import ru.job4j.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class RemoveUsersControllerTest {

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;

    private UserRepository repo;
    private User admin;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        DbConnector connector = DbConnector.getInstance();
        connector.cleanDB();
        connector.initDB();
        repo = new UserRepository();
        admin = createAdmin();
    }

    private User createUser() {
        return new User()
            .setId(3L)
            .setEmail("testuser@email.com")
            .setPassw("test1234")
            .setRole(new Role(3L, "USER"))
            .setAddress(new Address(2L, "France", "Toulouse", "54 rue Peyrolieres"))
            .setGenres(Arrays.asList(new MusicType(5L, "CLASSICAL"),
                new MusicType(2L, "ROCK")))
            .setCreateDate(Date.valueOf(LocalDate.now()));
    }

    private User createMandator(final Long id) {
        return new User()
            .setId(id)
            .setEmail("test" + id + "@email.com")
            .setPassw("test1234")
            .setRole(new Role(2L, "MANDATOR"))
            .setAddress(new Address(2L, "France", "Toulouse", "54 rue Peyrolieres"))
            .setGenres(Arrays.asList(new MusicType(5L, "CLASSICAL"),
                new MusicType(2L, "ROCK")))
            .setCreateDate(Date.valueOf(LocalDate.now()));
    }

    private User createAdmin() {
        return new User()
            .setId(2L)
            .setEmail("admin2@email.com")
            .setPassw("admin1234")
            .setRole(new Role(1L, "ADMIN"))
            .setAddress(new Address(1L, "Belgium", "Ghent", "Jakobijnenstraat 6"))
            .setGenres(Arrays.asList(new MusicType(1L, "RAP")))
            .setCreateDate(Date.valueOf(LocalDate.now()));
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
        repo.create(admin);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Roles.ADMIN));
        when(req.getParameter("id")).thenReturn("2");

        new RemoveUsersController().doPost(req, resp);

        verify(req, atLeastOnce()).getParameter("id");
        List<User> allUsers = repo.findAll();
        User u = allUsers.get(0);
        assertThat(allUsers.size(), is(1));
        assertThat(u.getEmail(), not("admin2@email.com"));
        assertThat(u.getRole().getType(), is(Roles.ADMIN.name()));
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenAdminRemovesMandatorThenMandatorRemoved()
        throws IOException, ServletException {
        repo.create(createMandator(2L));
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Roles.ADMIN));
        when(req.getParameter("id")).thenReturn("2");

        new RemoveUsersController().doPost(req, resp);

        verify(req, atLeastOnce()).getParameter("id");
        List<User> allUsers = repo.findAll();
        User u = allUsers.get(0);
        assertThat(allUsers.size(), is(1));
        assertThat(u.getEmail(), not("test@email.com"));
        assertThat(u.getRole().getType(), not(Roles.MANDATOR.name()));
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenMandatorRemovesAnotherMandatorThenErrorShown()
        throws IOException, ServletException {
        repo.create(createMandator(2L));
        repo.create(createMandator(3L));
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Roles.MANDATOR));
        when(req.getParameter("id")).thenReturn("3");
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new RemoveUsersController().doPost(req, resp);

        verify(req, atLeastOnce()).getParameter("id");
        List<User> allUsers = repo.findAll();
        User u = allUsers.get(2);
        assertThat(allUsers.size(), is(3));
        assertThat(u.getEmail(), is("test3@email.com"));
        assertThat(u.getRole().getType(), is(Roles.MANDATOR.name()));

        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to remove user");
        verify(resp, atLeastOnce()).sendRedirect(anyString());
    }

    @Test
    public void whenUserRemovesMandatorThenErrorShown()
        throws IOException, ServletException {
        repo.create(createMandator(2L));
        repo.create(createUser());

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn(String.valueOf(Roles.USER));
        when(req.getParameter("id")).thenReturn("3");
        when(req.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        new RemoveUsersController().doPost(req, resp);

        verify(req, atLeastOnce()).getParameter("id");
        List<User> allUsers = repo.findAll();
        User u = allUsers.get(2);
        assertThat(allUsers.size(), is(3));
        assertThat(u.getEmail(), is("testuser@email.com"));
        assertThat(u.getRole().getType(), is(Roles.USER.name()));

        verify(req, atLeastOnce()).setAttribute("error",
            "You don't have sufficient privileges to remove user");
        verify(resp, atLeastOnce()).sendRedirect(anyString());

    }
}