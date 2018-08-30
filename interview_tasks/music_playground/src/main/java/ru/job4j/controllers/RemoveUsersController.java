package ru.job4j.controllers;

import ru.job4j.UserRepository;
import ru.job4j.dao.MusicTypeDaoImpl;
import ru.job4j.dao.RoleDaoImpl;
import ru.job4j.dao.UserDaoImpl;
import ru.job4j.model.Roles;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RemoveUsersController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("users", new UserRepository().findAll());
        req.setAttribute("roles", new RoleDaoImpl().findAll());
        req.setAttribute("genres", new MusicTypeDaoImpl().findAll());
        req.setAttribute("role", req.getSession().getAttribute("role"));
        req.getRequestDispatcher("/WEB-INF/views/UsersView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        Roles currentRole = Roles.roleFromName(
            String.valueOf(session.getAttribute("role")));
        Long id = Long.parseLong(req.getParameter("id"));
        if (currentRole.equals(Roles.ADMIN)) {
            new UserDaoImpl().deleteById(id);
        } else {
            req.setAttribute("error",
                "You don't have sufficient privileges to remove user");
            doGet(req, resp);
        }
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}