package ru.aserdyuchenko.servlet;

import org.junit.Test;
import ru.aserdyuchenko.storage.DataSource;
import ru.aserdyuchenko.storage.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Serdyuchenko AN.
 * @since 01.01.2018
 */
public class ServletAddUserTest {

    @Test
    public void addUser() throws ServletException, IOException, SQLException {
        ServletAddUser servlet = new ServletAddUser();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("login")).thenReturn("Anton");

        servlet.doPost(request, response);

        List<User> users = DataSource.getInstance().getList();

        assertThat(users.get(users.size() - 1).getLogin(), is("Anton"));
    }
}