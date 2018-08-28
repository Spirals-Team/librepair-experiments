package ru.job4j.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 27.07.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class EditUserRoleServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(EditUserRoleServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int role = DbStore.getInstance().getUserRole(Integer.parseInt(req.getParameter("id")));
        Map<Integer, String> allRole = DbStore.getInstance().getAllRole();
        req.setAttribute("role", role);
        req.setAttribute("allRole", allRole);
        req.setAttribute("id", req.getParameter("id"));
        req.getRequestDispatcher("/WEB-INF/views/EditRoleView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DbStore.getInstance().updateUserRole(req.getParameter("id"), req.getParameter("update_role"));
        resp.sendRedirect(String.format("%s/list", req.getContextPath()));
    }
}
