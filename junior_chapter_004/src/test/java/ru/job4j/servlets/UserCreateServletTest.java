package ru.job4j.servlets;

import org.junit.Test;
import ru.job4j.model.User;
import ru.job4j.model.UserBuilder;
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
public class UserCreateServletTest {
    @Test
    public void addUser() throws ServletException, IOException {
        UserCreateServlet userCreateServlet = new UserCreateServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        DbStore dbStore = DbStore.getInstance();
        mock(dbStore.getClass());
        User user = new UserBuilder()
                .setLogin("user2")
                .build();

        when(request.getParameter("name")).thenReturn("user2");
        when(request.getParameter("login")).thenReturn("user2");
        when(request.getParameter("email")).thenReturn("user2@user2.ru");
        when(request.getParameter("password")).thenReturn("user2");

        when(dbStore.findByIdStore(6)).thenReturn(user);

        userCreateServlet.doPost(request, response);


        assertThat(dbStore.findByIdStore(6).getLogin(), is("user2"));
    }

}