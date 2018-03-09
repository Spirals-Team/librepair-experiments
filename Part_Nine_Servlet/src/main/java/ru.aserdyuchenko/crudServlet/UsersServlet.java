package ru.aserdyuchenko.crudServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class UsersServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UsersServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = new PrintWriter(response.getOutputStream());
        try {
            Class.forName("org.postgresql.Driver");
            Storage storage = new Storage();
            Map<String, User> users = storage.get();
            writer.append(users.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer.flush();
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            Class.forName("org.postgresql.Driver");
            Storage storage = new Storage();
            storage.add(request.getParameter("login"), request.getParameter("name"), request.getParameter("email"), request.getParameter("createDate"));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            Class.forName("org.postgresql.Driver");
            Storage storage = new Storage();
            storage.update(request.getParameter("name"), request.getParameter("email"), request.getParameter("createDate"), request.getParameter("login"));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        doGet(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            Class.forName("org.postgresql.Driver");
            Storage storage = new Storage();
            storage.delete(request.getParameter("login"));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
