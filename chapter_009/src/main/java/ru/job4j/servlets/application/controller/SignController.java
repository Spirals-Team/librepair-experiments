package ru.job4j.servlets.application.controller;

import ru.job4j.servlets.application.service.UserStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * /singin
 * @author Hincu Andrei (andreih1981@gmail.com)on 28.01.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class SignController extends HttpServlet {
    private final UserStorage userStorage = UserStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/LoginView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (userStorage.isCredential(login, password)) {
           HttpSession session = req.getSession();
            session.setAttribute("login", login);
            resp.sendRedirect(String.format("%s/", req.getContextPath()));
        } else {
            req.setAttribute("error", "Identification is invalid.");
            doGet(req, resp);
        }
    }

    @Override
    public void destroy() {
        this.userStorage.close();
    }
}
