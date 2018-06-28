package zuryanov.servlets.presentation;

import org.junit.Test;
import zuryanov.servlets.logic.ValidateService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserCreateControllerTest {

    String path = "/WEB-INF/views/create.jsp";

    @Test
    public void userCreate() {
        UserCreateController controler = new UserCreateController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        ValidateService.getInstance().add("name");
        assertThat(ValidateService.getInstance().findById(ValidateService.getInstance().findByName("name")), is("name"));

    }
}