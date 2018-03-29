package ru.job4j.servlets;

import ru.job4j.models.User;
import ru.job4j.resourses.UserStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UsersServlet extends HttpServlet {
    private UserStorage storage = UserStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        List<User> list = storage.getAllUsers();

        out.println("<h1>Users List</h1>");
        out.print("<table border='1' width='100%'>");
        out.print("<tr><th>Id</th><th>Name</th><th>Login</th></tr>");
        for(User user : list){
            out.print("<tr><td>" + user.getId() + "</td>");
            out.print("<td>" + user.getName() + "</td>");
            out.print("<td>" + user.getLogin() + "</td></tr>");
        }
        out.print("</table>");

        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        User user = new User(name, login);
        storage.insertUser(user);
        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sid = req.getParameter("id");
        int id = Integer.parseInt(sid);
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        User user = new User(name, login);
        user.setId(id);
        storage.updateUser(user);
        doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stringId = req.getParameter("id");
        int id = Integer.parseInt(stringId);
        storage.deleteUser(id);
        doGet(req, resp);
    }
}
