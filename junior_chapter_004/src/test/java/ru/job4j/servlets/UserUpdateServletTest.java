package ru.job4j.servlets;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.service.Validate;
import ru.job4j.service.ValidateService;
import ru.job4j.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 31.07.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class UserUpdateServletTest {
    private static final Logger LOG = LoggerFactory.getLogger(UserUpdateServletTest.class);

    @Test
    public void updateUser() throws ServletException, IOException {
        UserUpdateServlet userUpdateServlet = new UserUpdateServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ValidateService validateService = mock(ValidateService.class);


        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("name")).thenReturn("user2");
        when(request.getParameter("login")).thenReturn("user2@user2.ru");
        when(request.getParameter("email")).thenReturn("user2");
        when(request.getParameter("password")).thenReturn("user2");

        //when(validateService.updateValidate(1, "user2", "user2", "user2@user2.ru", "user2")).thenReturn(true);

        userUpdateServlet.doPost(request, response);

        //assertThat(validateService.updateValidate(1, "user2", "user2", "user2@user2.ru", "user2"), is(true));

    }
}
