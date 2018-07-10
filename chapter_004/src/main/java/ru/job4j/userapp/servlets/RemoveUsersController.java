package ru.job4j.userapp.servlets;

import ru.job4j.userapp.Role;
import ru.job4j.userapp.RoleDaoImpl;
import ru.job4j.userapp.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Remove user's servlet
 */
public class RemoveUsersController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("users", new UserDaoImpl().getAllUsers());
        req.setAttribute("roles", new RoleDaoImpl().getAllRoles());
        req.setAttribute("role", req.getSession().getAttribute("role"));
        req.getRequestDispatcher("/WEB-INF/views/UsersView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        Role currentRole = Role.roleFromName(
            String.valueOf(session.getAttribute("role")));
        Integer id = Integer.parseInt(req.getParameter("id"));
        if (currentRole.equals(Role.ADMIN)) {
            new UserDaoImpl().deleteUser(id);
        } else {
            req.setAttribute("error",
                "You don't have sufficient privileges to remove user");
            doGet(req, resp);
        }
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}
