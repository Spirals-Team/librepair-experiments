package ru.job4j.jstl;

import org.junit.Test;
import ru.job4j.crudservlet.User;
import ru.job4j.servlet.UserStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class DelUserTest {
    @Test
    public void delUser() throws ServletException, IOException {
        DelUser delUser = new DelUser();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("login")).thenReturn("");
        String login = "SuperPuper";
        UserStore.getInstance().addUser(new User("Test", login, "super@puper", "test"));
        String id = UserStore.getInstance().getUser(login).getId();
        when(req.getParameter("del_id")).thenReturn(id);
        delUser.doPost(req, resp);
        User user = UserStore.getInstance().getUser(login);
        User exist = null;
        assertThat(user, is(exist));
    }
}