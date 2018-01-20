package ua.com.company.store.controller.impl.redirection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.com.company.store.constants.Redirection;
import ua.com.company.store.utils.CookiesAction;
import ua.com.company.store.utils.RedirectionManager;
import ua.com.company.store.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Владислав on 03.01.2018.
 */
public class CommandHomePageCommandTest {
    private UserService userService;
    private CommandHomePageCommand commandHomePageCommand;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private CookiesAction cookiesAction;
    @Before
    public void setUp() throws Exception {
        initObjectsMocking();
        commandHomePageCommand = new CommandHomePageCommand(userService);

    }
    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void execute() throws Exception {
        when(request.getSession()).thenReturn(mock(HttpSession.class));
        Cookie[] cookies= new Cookie[]{mock(Cookie.class)};
        when(request.getCookies()).thenReturn(cookies);
        CookiesAction cookiesAction = mock(CookiesAction.class);


        String expexted = commandHomePageCommand.execute(request,response);
        String actual = Redirection.MAIN_PAGE+ " " + RedirectionManager.REDIRECTION;

        assertEquals(expexted,actual);

        verify(cookiesAction).getUserFromCookie(request,userService);
       // verify(session).setAttribute(anyString(),mock(User.class));
    }
    private void initObjectsMocking() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        userService = mock(UserService.class);
        session = mock(HttpSession.class);
        cookiesAction = mock(CookiesAction.class);
    }

}