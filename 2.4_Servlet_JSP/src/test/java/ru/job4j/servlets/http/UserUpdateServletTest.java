package ru.job4j.servlets.http;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserUpdateServletTest {
    private final String login = "LoginForTest";
    private final String name = "NameForTest";

    /**
     * Before test.
     */
    @Before
    public void before() {
        ValidateService.getInstance().add(null, login, null, null, null);
    }

    /**
     * After test.
     */
    @After
    public void after() {
        ValidateService.getInstance().delete(login);
    }

    /**
     * Test editing user.
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Test
    public void editUser() throws ServletException, IOException {
        UserUpdateServlet create = new UserUpdateServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        String expect = ValidateService.getInstance().findByLogin(login).getName();
        String result = null;
        assertThat(expect, is(result));

        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("name")).thenReturn(name);
        create.doPost(request, response);
        expect = name;
        result = ValidateService.getInstance().findByLogin(login).getName();
        assertThat(expect, is(result));
    }
}