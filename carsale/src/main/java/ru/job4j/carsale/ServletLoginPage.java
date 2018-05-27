package ru.job4j.carsale;

import ru.job4j.models.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ServletLoginPage extends HttpServlet {
    private final CarStorage carStorage = CarStorage.INSTANCE;
    private int id = -1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/LoginPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        List<User> list = carStorage.getList(User.class.getSimpleName());
        if (isAccess(list, login, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("login", login);
            session.setAttribute("user_id", id);
            resp.sendRedirect("mainpage");
        } else {
            req.setAttribute("login", login);
            req.setAttribute("error", "invalid access");
            this.doGet(req, resp);
        }
    }

    private boolean isAccess(List<User> list, String login, String password) {
        boolean access = false;
        for (User user : list) {
            if (user.getLogin().equalsIgnoreCase(login) && user.getPassword().equals(password)) {
                id = user.getId();
                access = true;
                break;
            }
        }
        return access;
    }
}
