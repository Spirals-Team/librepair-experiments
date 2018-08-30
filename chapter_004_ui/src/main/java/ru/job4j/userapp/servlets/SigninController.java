package ru.job4j.userapp.servlets;

import ru.job4j.userapp.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Signin servlet.
 */
public class SigninController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/WEB-INF/views/LoginView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String passw = req.getParameter("passw");
        if (new UserDaoImpl().isCredential(login, passw)) {
            HttpSession session = req.getSession();
            synchronized (session) {
                session.setAttribute("login", login);
                session.setAttribute("role",
                    new UserDaoImpl().getUserRole(login, passw));
            }
            resp.sendRedirect(String.format("%s/", req.getContextPath()));
        } else {
            req.setAttribute("error", "Credentials is invalid");
            doGet(req, resp);
        }
    }
}
