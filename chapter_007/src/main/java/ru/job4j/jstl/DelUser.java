package ru.job4j.jstl;

import ru.job4j.servlet.UserStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class DelUser extends HttpServlet {
    private final UserStore users = UserStore.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("del_id");
        if (id != null) {
            users.delUser(id);
        }
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}
