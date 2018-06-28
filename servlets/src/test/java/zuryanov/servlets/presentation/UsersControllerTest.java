package zuryanov.servlets.presentation;

import org.junit.Test;
import zuryanov.servlets.logic.ValidateService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsersControllerTest {

    String path = "/WEB-INF/views/list.jsp";

    @Test
    public void userCreate() throws ServletException, IOException {
        UsersController controler = new UsersController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("user")).thenReturn("root");
        when(request.getParameter("submit")).thenReturn("delete");
        controler.doPost(request, response);
        assertThat(ValidateService.getInstance().findByName("root"), is(0));
    }
}