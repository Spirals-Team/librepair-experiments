package ru.job4j.servlets;

import ru.job4j.resourses.UserStorage;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class UsersServlet extends HttpServlet {
    private UserStorage userStorage = new UserStorage();

    public UsersServlet() throws SQLException {
    }

    @Override
    protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        try {
            String result = "<p>" + userStorage.getData() + "</p>";
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.append(result);
            writer.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

  /*  @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html");
//        String name = req.getParameter("name");
//        String login = req.getParameter("login");
//        userStorage.insertUser(name, login);
        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String name = req.getParameter("name");
//        userStorage.delete(name);
//        doGet(req, resp);
    }*/
}
