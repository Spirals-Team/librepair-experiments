package ru.job4j.servlets.application.controller;

import ru.job4j.servlets.application.model.User;
import ru.job4j.servlets.application.service.UserStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 23.01.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class ForwardServlet extends HttpServlet {
    private UserStorage userStorage = UserStorage.getInstance();

    @Override
    public void destroy() {
        userStorage.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("update".equalsIgnoreCase(action)) {
            req.setAttribute("title", "Обновление данных пользователя.");
            req.setAttribute("roles", userStorage.getRoles());
            req.setAttribute("path", String.format("%s/edit", req.getContextPath()));
            User user = new User(req.getParameter("login"), req.getParameter("name"), req.getParameter("email"),
                             req.getParameter("password"), req.getParameter("role"));
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/views/UserForm.jsp").forward(req, resp);
        }
        if ("delete".equalsIgnoreCase(action)) {
            req.setAttribute("ask", req.getParameter("login"));
            req.setAttribute("title", String.format("Удалить пользователя с логином %s ?", req.getParameter("login")));
            req.getRequestDispatcher("/WEB-INF/views/responsePage.jsp").forward(req, resp);
        }
        if ("add new user".equalsIgnoreCase(action)) {
            req.setAttribute("title", "Добавление нового пользователя.");
            req.setAttribute("roles", userStorage.getRoles());
            req.setAttribute("path", String.format("%s/new", req.getContextPath()));
            req.getRequestDispatcher("/WEB-INF/views/UserForm.jsp").forward(req, resp);
        }
    }
}
