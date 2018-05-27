package ru.job4j.carsale;

import ru.job4j.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ServletAddUserPage extends HttpServlet {
    private final CarStorage carStorage = CarStorage.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/AddUserPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");
        req.setAttribute("login", login);
        if (!password.equals(password2)) {
            req.setAttribute("error", "Пароли не совпадают.");
            this.doGet(req, resp);
        } else if (password.matches("^[a-zA-z0-9]") || password.length() < 8) {
            req.setAttribute("error", "Длина пароля должна быть не менее 8 символов. Обязатльно из букв и/или цифр.");
            this.doGet(req, resp);
        } else {
            List<User> list = carStorage.getList(User.class.getSimpleName());
            for (User user : list) {
                if (user.getLogin().equalsIgnoreCase(login)) {
                    req.setAttribute("error", "Такой логин уже занят.");
                    this.doGet(req, resp);
                }
            }
            carStorage.addObject(new User(login, password));
            resp.sendRedirect(String.format("login?login=%s", login));
        }
    }
}
