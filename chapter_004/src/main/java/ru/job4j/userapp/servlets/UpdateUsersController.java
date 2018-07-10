package ru.job4j.userapp.servlets;

import ru.job4j.userapp.Role;
import ru.job4j.userapp.RoleDaoImpl;
import ru.job4j.userapp.User;
import ru.job4j.userapp.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Update user's servlet
 */
public class UpdateUsersController extends HttpServlet {

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
        String currentLogin = String.valueOf(session.getAttribute("login"));
        Integer id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String passw = req.getParameter("passw");
        String email = req.getParameter("email");
        Role role = Role.roleFromName(req.getParameter("role"));
        User user = new User(id, name, login, passw, email, role);
        if (currentRole.equals(Role.ADMIN)) {
            new UserDaoImpl().updateUser(user);
        } else if (isUserHasPrivileges(currentLogin, currentRole, user.getLogin())) {
            new UserDaoImpl().updateUser(user);
        } else {
            req.setAttribute("error",
                "You don't have sufficient privileges to edit user");
            doGet(req, resp);
        }
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }

    private boolean isUserHasPrivileges(final String currentLogin,
                                        final Role currentRole, final String userLogin) {
        return currentRole.equals(Role.USER) && currentLogin.equals(userLogin)
            && !currentRole.equals(Role.ADMIN);
    }
}
