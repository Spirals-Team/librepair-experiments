package ru.job4j.controllers;

import ru.job4j.UserRepository;
import ru.job4j.dao.MusicTypeDaoImpl;
import ru.job4j.dao.RoleDaoImpl;
import ru.job4j.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Add user's servlet.
 */
public class AddUsersController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("roles", new RoleDaoImpl().findAll());
        req.setAttribute("genres", new MusicTypeDaoImpl().findAll());
        req.getRequestDispatcher("/WEB-INF/views/AddUserView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        String currentRole =
            String.valueOf(req.getSession().getAttribute("role"));
        String email = req.getParameter("email");
        String passw = req.getParameter("passw");
        Role role = new Role().setType(req.getParameter("role"));
        Address address = new Address()
            .setCountry(req.getParameter("country"))
            .setCity(req.getParameter("city"))
            .setStreetAddress(req.getParameter("street_address"));
        List<MusicType> genres = Arrays.stream(Optional.ofNullable(
            req.getParameterValues("genres")).orElse(new String[]{}))
            .map(MusicType::new).collect(Collectors.toList());
        User user = new User(email, passw, role, address, genres);
        if (currentRole.equals(Roles.ADMIN.name())) {
            new UserRepository().create(user);
        } else if (currentRole.equals(Roles.MANDATOR.name())
            && !role.getType().equals(Roles.ADMIN.name())) {
            new UserRepository().create(user);
        } else {
            req.setAttribute("error",
                String.format("You don't have sufficient privileges to set role: %s", role.getType()));
            doGet(req, resp);
        }
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}