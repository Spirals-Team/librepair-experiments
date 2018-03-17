package ru.job4j.servlets.application.controller;

import ru.job4j.servlets.application.service.UserStorage;
import ru.job4j.servlets.application.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Начальное отображение.
 * @author Hincu Andrei (andreih1981@gmail.com)on 14.01.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class UsersController extends HttpServlet {
    private  final UserStorage userStorage = UserStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String) req.getSession().getAttribute("login");
        if (userStorage.isAdmin(login)) {
            req.setAttribute("users", userStorage.selectUsers());
            HttpSession session = req.getSession();
            session.setAttribute("admin", "admin");
        } else {
            List<User> user = new CopyOnWriteArrayList<>();
            user.add(userStorage.getUser(login));
            req.setAttribute("users", user);
        }
        req.getRequestDispatcher("/WEB-INF/views/UsersView.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        userStorage.close();
    }
}
