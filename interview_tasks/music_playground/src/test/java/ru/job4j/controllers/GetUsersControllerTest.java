package ru.job4j.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.job4j.DbConnector;
import ru.job4j.model.MusicType;
import ru.job4j.model.Role;
import ru.job4j.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetUsersControllerTest {

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

        new GetUsersController().doGet(req, resp);

        verify(req, atLeastOnce()).getSession();
        verify(session, atLeastOnce()).getAttribute("role");
        verify(req, atLeastOnce()).setAttribute(anyString(), anyCollectionOf(User.class));
        verify(req, atLeastOnce()).setAttribute(anyString(), anyCollectionOf(Role.class));
        verify(req, atLeastOnce()).setAttribute(anyString(), anyCollectionOf(MusicType.class));
        verify(req, atLeastOnce()).setAttribute(anyString(), any(String.class));
        verify(req, atLeastOnce()).getRequestDispatcher("/WEB-INF/views/UsersView.jsp");
    }
}