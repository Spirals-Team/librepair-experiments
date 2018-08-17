package ru.job4j.servlets.http;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Class UsersServletTest.
 */
public class UsersServletTest {
    private final String login = "LoginForTest";
    /**
     * before test.
     */
    @Before
    public void before() {
        ValidateService.getInstance().add(null, login, null, null, null);
    }

    /**
     * test deleting user.
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Test
    public void deleteUser() throws ServletException, IOException {
        UsersServlet servlet = new UsersServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        String id = String.valueOf(ValidateService.getInstance().findByLogin(login).getId());

        int expect = ValidateService.getInstance().findAll().size();
        when(request.getParameter("id")).thenReturn(id);
        servlet.doPost(request, response);

        int result = ValidateService.getInstance().findAll().size();
        assertThat(expect, is(result + 1));
    }
}