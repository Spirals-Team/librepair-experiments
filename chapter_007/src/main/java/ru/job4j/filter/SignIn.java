package ru.job4j.filter;

import ru.job4j.servlet.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SignIn extends HttpServlet {
    private final UserStore users = UserStore.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/Login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        if (users.isEnter(login, email)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", users.getUser(login));
            resp.sendRedirect(String.format("%s/", req.getContextPath()));
        } else {
            req.setAttribute("login", login);
            req.setAttribute("email", email);
            req.setAttribute("error", "invalid access");
            this.doGet(req, resp);
        }
    }
}
