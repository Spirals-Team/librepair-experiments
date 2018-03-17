package ru.job4j.servlets.application.controller;

import ru.job4j.servlets.application.service.UserStorage;
import ru.job4j.servlets.application.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * /new
 * Добавление нового пользователя.
 * @author Hincu Andrei (andreih1981@gmail.com)on 14.01.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class Post extends HttpServlet {
    private  final UserStorage userStorage = UserStorage.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("newLogin");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("select");
        User user;
        if (login.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            user = new User(login, name, email, password, role);
            req.setAttribute("user",  user);
            req.setAttribute("title", "Заполните все данные.");
            req.getRequestDispatcher("/WEB-INF/views/UserForm.jsp").forward(req, resp);
        } else {
            user = this.userStorage.getUser(login);
            if (user != null) {
                req.setAttribute("title", "Пользователь с таким логином уже существует.");
            } else {
                user  = new User(login, name, email, password, role);
                this.userStorage.addNewUser(user);
                req.setAttribute("title", "Пользователь успешно добавлен.");
            }
            req.getRequestDispatcher("/WEB-INF/views/responsePage.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
        userStorage.close();
    }
}
