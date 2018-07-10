package ru.job4j.userapp.servlets;

import ru.job4j.userapp.Role;
import ru.job4j.userapp.RoleDaoImpl;
import ru.job4j.userapp.User;
import ru.job4j.userapp.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Add user's servlet.
 */
public class AddUsersController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("roles", new RoleDaoImpl().getAllRoles());
        req.getRequestDispatcher("/WEB-INF/views/AddUserView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        Role currentRole = Role.roleFromName(
            String.valueOf(req.getSession().getAttribute("role")));
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String passw = req.getParameter("passw");
        String email = req.getParameter("email");
        String region = req.getParameter("region");
        String country = req.getParameter("country");
        Role role = Role.roleFromName(req.getParameter("role"));
        User user = new User(name, login, passw, email, region, country, role);
        if (currentRole.equals(Role.ADMIN)) {
            new UserDaoImpl().insertUser(user);
        } else if (currentRole.equals(Role.USER) && !role.equals(Role.ADMIN)) {
            new UserDaoImpl().insertUser(user);
        } else {
            req.setAttribute("error",
                String.format("You don't have sufficient privileges to set role: %s", role));
            doGet(req, resp);
        }
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}
