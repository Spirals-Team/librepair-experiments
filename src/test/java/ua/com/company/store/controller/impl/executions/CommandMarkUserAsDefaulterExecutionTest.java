package ua.com.company.store.controller.impl.executions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.com.company.store.constants.Redirection;
import ua.com.company.store.model.entity.User;
import ua.com.company.store.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Владислав on 04.01.2018.
 */
public class CommandMarkUserAsDefaulterExecutionTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserService userService;
    private CommandMarkUserAsDefaulterExecution commandMarkUserAsDefaulterExecution;
    @Before
    public void setUp() throws Exception {
createMockObjects();
commandMarkUserAsDefaulterExecution = new CommandMarkUserAsDefaulterExecution(userService);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void execute() throws Exception {

    }

    @Test
    public void accessOk() throws ServletException, IOException {
        User user = mock(User.class);
        HttpSession session = mock(HttpSession.class);
        List list = mock(List.class);
        when(request.getAttribute(anyString())).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(user);
        when(user.isRole()).thenReturn(true);
        when(request.getParameter(anyString())).thenReturn("0");
        when(userService.getById(anyInt())).thenReturn(user);
        when(userService.getAllUsers()).thenReturn(list);



        String expected = commandMarkUserAsDefaulterExecution.execute(request,response);
        String actual = Redirection.ADMIN_PAGE;

        assertEquals(expected,actual);
         verify(request).setAttribute(anyString(),eq(list));
        verify(userService).markUserAsDefaulter(user);

    }
    private void createMockObjects(){
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        userService = mock(UserService.class);
    }

}