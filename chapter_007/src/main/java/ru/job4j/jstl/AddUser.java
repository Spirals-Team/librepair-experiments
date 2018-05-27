package ru.job4j.jstl;

import ru.job4j.crudservlet.User;
import ru.job4j.servlet.UserStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class AddUser extends HttpServlet {
    private final UserStore users = UserStore.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        req.setAttribute("name", name);
        req.setAttribute("login", login);
        req.setAttribute("email", email);
        if (name == null || name.equals("") || login == null || login.equals("") || email == null || email.equals("")) {
            req.setAttribute("error", "Enter NULL value!");
            req.getRequestDispatcher("/WEB-INF/views/add.jsp").forward(req, resp);
        } else if (users.isDublicat(login)) {
            req.setAttribute("error", "This Login is busy");
            req.getRequestDispatcher("/WEB-INF/views/add.jsp").forward(req, resp);
        } else {
            users.addUser(new User(name, login, email, "user"));
            resp.sendRedirect(String.format("%s/", req.getContextPath()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/add.jsp").forward(req, resp);
    }
}
