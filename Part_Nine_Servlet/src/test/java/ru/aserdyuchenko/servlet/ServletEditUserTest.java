package ru.aserdyuchenko.servlet;

import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServletEditUserTest {
    @Test
    public void editUser() throws SQLException {
        ServletEditUser servlet = new ServletEditUser();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        if (!DataSource.getInstance().getMap().containsKey("test")) {
            DataSource.getInstance().add("test", "test", "test", "test", "test", "admin");
        }

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("role")).thenReturn("admin");
        when(request.getParameter("role")).thenReturn("test");
        when(request.getParameter("login")).thenReturn("test");
        when(request.getParameter("name")).thenReturn("edit");
        when(request.getParameter("email")).thenReturn("edit");
        when(request.getParameter("createDate")).thenReturn("edit");
        when(request.getParameter("password")).thenReturn("edit");

        servlet.doPost(request, response);
        assertThat(DataSource.getInstance().getMap().get("test").getName(), is("edit"));
    }
}