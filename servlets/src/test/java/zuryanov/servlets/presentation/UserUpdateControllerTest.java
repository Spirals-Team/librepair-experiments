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

public class UserUpdateControllerTest {

    String path = "/WEB-INF/views/list.jsp";

    @Test
    public void userUpdate() throws ServletException, IOException {
        UserUpdateController controler = new UserUpdateController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("login")).thenReturn("root_new");
        ValidateService.getInstance().add("root");
        controler.doPost(request, response);

        assertThat(ValidateService.getInstance().findByName("root_new"), is(1));
    }
}