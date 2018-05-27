package ru.job4j.jstl;

import ru.job4j.crudservlet.User;
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
public class EditUser extends HttpServlet {
    private final UserStore users = UserStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String role = req.getParameter("role");
        String mainrole = req.getParameter("mainrole");
        if (name == null || name.equals("") || email == null || email.equals("")) {
            req.setAttribute("id", id);
            req.setAttribute("name", name);
            req.setAttribute("login", login);
            req.setAttribute("email", email);
            req.setAttribute("role", role);
            req.setAttribute("mainrole", mainrole);
            req.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req, resp);
        } else {
            users.updateUser(id, new User(name, login, email, role));
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null && user.getLogin().equals(login)) {
                session.setAttribute("user", users.getUser(login));
            }
            resp.sendRedirect(String.format("%s/", req.getContextPath()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String role = req.getParameter("role");
        String mainrole = req.getParameter("mainrole");
        req.setAttribute("id", id);
        req.setAttribute("name", name);
        req.setAttribute("login", login);
        req.setAttribute("email", email);
        req.setAttribute("role", role);
        req.setAttribute("mainrole", mainrole);
        req.setAttribute("roles", users.getRoles());
        req.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req, resp);
    }
}
