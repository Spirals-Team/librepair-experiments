package ru.job4j.servlets.http;

import org.junit.After;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserCreateServletTest {
    private String login = "LoginForTest";
    /**
     * After test.
     */
    @After
    public void after() {
        ValidateService.getInstance().delete(login);
    }

    /**
     * Test adding user.
     * @throws ServletException exception
     * @throws IOException exception
     */
    @Test
    public void addUser() throws ServletException, IOException {
        UserCreateServlet create = new UserCreateServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        int expect = ValidateService.getInstance().findAll().size();
        when(request.getParameter("login")).thenReturn(login);
        create.doPost(request, response);
        int result = ValidateService.getInstance().findAll().size();
        assertThat(expect, is(result - 1));
    }
}