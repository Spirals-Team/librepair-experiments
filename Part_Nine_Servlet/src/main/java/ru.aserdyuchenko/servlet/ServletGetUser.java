package ru.aserdyuchenko.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aserdyuchenko.storage.DataSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 * @since 05.11.2017 18:30
 */
public class ServletGetUser extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ServletGetUser.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            synchronized (session) {
                request.setAttribute("role", DataSource.getInstance().getMap().get(session.getAttribute("login")).getRole());
                request.setAttribute("users", DataSource.getInstance().getList());
            }
            request.getRequestDispatcher("/get.html").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
