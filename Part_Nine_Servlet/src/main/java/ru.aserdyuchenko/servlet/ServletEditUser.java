package ru.aserdyuchenko.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 * @since 05.11.2017 18:30
 */
public class ServletEditUser extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ServletEditUser.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            synchronized (session) {
                request.setAttribute("role", DataSource.getInstance().getMap().get(session.getAttribute("login")).getRole());
                request.setAttribute("login", session.getAttribute("login"));
                request.setAttribute("users", DataSource.getInstance().getList());
            }
            request.getRequestDispatcher("/WEB-INF/views/editUser.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        try {
            HttpSession session = request.getSession();
            synchronized (session) {
                Class.forName("org.postgresql.Driver");
                DataSource storage = DataSource.getInstance();
                if (session.getAttribute("role").toString().equals("admin")) {
                    storage.update(request.getParameter("name"), request.getParameter("email"), request.getParameter("createDate"), request.getParameter("login"), request.getParameter("password"), request.getParameter("role"));
                } else if(session.getAttribute("role").toString().equals("user")) {
                    storage.update(request.getParameter("name"), request.getParameter("email"), request.getParameter("createDate"), session.getAttribute("login").toString(), request.getParameter("password"), request.getParameter("role"));
                }
                response.sendRedirect(String.format("%s/editUser", request.getContextPath()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
