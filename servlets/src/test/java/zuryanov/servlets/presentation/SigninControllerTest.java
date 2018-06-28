package zuryanov.servlets.presentation;

import org.junit.Test;
import zuryanov.servlets.logic.ValidateService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SigninControllerTest {

    String path = "/WEB-INF/views/login.jsp";

    @Test
    public void enterAdmin() {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        assertThat(ValidateService.getInstance().isCredentional("root", "root"), is(true));
    }
}