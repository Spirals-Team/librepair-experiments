package ru.job4j.controllers;

import ru.job4j.UserRepository;
import ru.job4j.dao.MusicTypeDaoImpl;
import ru.job4j.dao.RoleDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetUsersController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", new UserRepository().findAll());
        req.setAttribute("roles", new RoleDaoImpl().findAll());
        req.setAttribute("genres", new MusicTypeDaoImpl().findAll());
        req.setAttribute("role", req.getSession().getAttribute("role"));
        req.getRequestDispatcher("/WEB-INF/views/UsersView.jsp").forward(req, resp);
    }
}
