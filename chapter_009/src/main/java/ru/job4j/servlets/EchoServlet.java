package ru.job4j.servlets;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 06.01.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class EchoServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(EchoServlet.class);
    private List<String> users = new CopyOnWriteArrayList<String>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));

        StringBuilder sb = new StringBuilder("<table>");
        for (String login : this.users) {
            sb.append("<tr><td>"
                    + login
                    + "</td></tr>");
        }
        sb.append("</table>");
        writer.append("<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <title>My First Web Page!</title>"
                + "</head>"
                + "<body>"
                + "<h1>Хранилище пользователей</h1>"
                + "<form action='"
                + req.getContextPath()
                + "/echo' method='post'>"
                + "Name : <input type='text' name='login'>"
                + "<input type='submit'>"
                + "</form>"
                + "<br/>"
                + sb.toString()
                + "</body>"
                + "</html>");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        this.users.add(req.getParameter("login"));
        doGet(req, resp);
    }
}
