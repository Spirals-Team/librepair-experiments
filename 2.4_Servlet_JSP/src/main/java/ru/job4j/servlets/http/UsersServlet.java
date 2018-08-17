package ru.job4j.servlets.http;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Class UserServlet.
 *
 * @author ifedorenko
 * @since 06.08.2018
 */
@WebServlet(name = "UsersServlet")
public class UsersServlet extends HttpServlet {
    private final ValidateService vs = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        synchronized (session) {
            if (session.getAttribute("role").equals("admin")) {
                request.setAttribute("users", vs.findAll());
                request.getRequestDispatcher("/WEB-INF/views/adminList.jsp").forward(request, response);
            } else {
                request.setAttribute("user", vs.findByLogin((String) session.getAttribute("login")));
                request.getRequestDispatcher("/WEB-INF/views/userList.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        int id = Integer.valueOf(req.getParameter("id"));
        vs.delete(id);
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}
