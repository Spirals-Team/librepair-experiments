package ru.aserdyuchenko.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aserdyuchenko.storage.DataSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 * @since 05.11.2017 18:30
 */
public class ServletAddUser extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ServletAddUser.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("users", DataSource.getInstance().getList());
            request.getRequestDispatcher("/WEB-INF/views/addUser.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            Class.forName("org.postgresql.Driver");
            DataSource storage = DataSource.getInstance();
            storage.add(request.getParameter("login"), request.getParameter("name"), request.getParameter("email"), request.getParameter("createDate"), request.getParameter("password"), request.getParameter("role"));
            response.sendRedirect(String.format("%s/addUser", request.getContextPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
