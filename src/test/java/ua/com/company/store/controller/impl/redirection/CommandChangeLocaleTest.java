package ua.com.company.store.controller.impl.redirection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.com.company.store.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by Владислав on 03.01.2018.
 */
public class CommandChangeLocaleTest {
    private UserService userService;
    private CommandAdminPage commandAdminPage;
    private HttpServletRequest request;
    private HttpServletResponse response;


    @Before
    public void setUp() throws Exception {
        initObjectsMocking();
        commandAdminPage = new CommandAdminPage(userService);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void execute() throws Exception {

    }

    private void initObjectsMocking() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        userService = mock(UserService.class);
    }

}