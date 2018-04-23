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
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServletDeleteUserTest {
    @Test
    public void deleteUser() throws ServletException, IOException, SQLException {
        ServletDeleteUser servlet = new ServletDeleteUser();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        List<User> users = DataSource.getInstance().getList();

        User user = users.get(users.size() - 1);

        when(request.getParameter("login")).thenReturn(user.getLogin());

        servlet.doPost(request, response);

        assertFalse(DataSource.getInstance().getList().contains(user));
    }
}