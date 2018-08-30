package ru.job4j.userapp.servlets;

import ru.job4j.userapp.RoleDaoImpl;
import ru.job4j.userapp.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User's servlet
 */
public class GetUsersController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", new UserDaoImpl().getAllUsers());
        req.setAttribute("roles", new RoleDaoImpl().getAllRoles());
        req.setAttribute("role", req.getSession().getAttribute("role"));
        req.getRequestDispatcher("/WEB-INF/views/UsersView.jsp").forward(req, resp);
    }
}
