package ru.job4j.controllers;

import ru.job4j.UserRepository;
import ru.job4j.model.User;

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
        String email = req.getParameter("email");
        String passw = req.getParameter("passw");
        User user = new UserRepository().findByEmailPassw(email, passw);
        if (!user.isEmpty()) {
            HttpSession session = req.getSession();
            synchronized (session) {
                session.setAttribute("email", email);
                session.setAttribute("role", user.getRole().getType());
            }
            resp.sendRedirect(String.format("%s/", req.getContextPath()));
        } else {
            req.setAttribute("error", "Credentials is invalid");
            doGet(req, resp);
        }
    }
}