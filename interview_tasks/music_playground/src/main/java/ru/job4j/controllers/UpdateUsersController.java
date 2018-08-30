package ru.job4j.controllers;

import ru.job4j.UserRepository;
import ru.job4j.dao.MusicTypeDaoImpl;
import ru.job4j.dao.RoleDaoImpl;
import ru.job4j.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Update user's servlet
 */
public class UpdateUsersController extends HttpServlet {

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
        String currentEmail = String.valueOf(session.getAttribute("email"));
        Long id = Long.parseLong(req.getParameter("id"));
        String email = req.getParameter("email");
        String passw = req.getParameter("passw");
        Role role = new Role().setType(req.getParameter("role"));
        Address address = new Address()
            .setCountry(req.getParameter("country"))
            .setCity(req.getParameter("city"))
            .setStreetAddress(req.getParameter("street_address"));
        List<MusicType> genres =
            Arrays.stream(req.getParameterValues("genres"))
                .map(MusicType::new).collect(Collectors.toList());
        User user = new User(id, email, passw, role, address, genres);
        if (currentRole.equals(Roles.ADMIN)) {
            new UserRepository().update(user);
        } else if (isUserHasPrivileges(currentEmail, currentRole, user.getEmail())) {
            new UserRepository().update(user);
        } else {
            req.setAttribute("error",
                "You don't have sufficient privileges to edit user");
            doGet(req, resp);
        }
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }

    private boolean isUserHasPrivileges(final String currentLogin,
                                        final Roles currentRole, final String userLogin) {
        return currentRole.equals(Roles.MANDATOR) && currentLogin.equals(userLogin)
            && !currentRole.equals(Roles.ADMIN);
    }
}