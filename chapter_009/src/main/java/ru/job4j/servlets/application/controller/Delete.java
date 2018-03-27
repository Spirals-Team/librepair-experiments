package ru.job4j.servlets.application.controller;

import ru.job4j.servlets.application.service.UserStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Удаление пользователя.
 * @author Hincu Andrei (andreih1981@gmail.com)on 14.01.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class Delete extends HttpServlet {
    private UserStorage userStorage = UserStorage.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        this.userStorage.deleteUser(login);
        req.setAttribute("title", "Пользователь был успешно удален.");
        req.getRequestDispatcher("/WEB-INF/views/responsePage.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        userStorage.close();
    }
}
