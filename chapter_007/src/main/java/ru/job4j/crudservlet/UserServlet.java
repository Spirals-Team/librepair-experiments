package ru.job4j.crudservlet;

import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class UserServlet extends HttpServlet {
    private final static Logger LOG = Logger.getLogger(UserServlet.class);
    private final UserStore users = UserStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("do GET:");
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append("<table border='1'>");
        for (User user : users.getUsers()) {
            writer.append("<tr>");
            writer.append(String.format("<td>%s</td>", user.getId()));
            writer.append(String.format("<td>%s</td>", user.getName()));
            writer.append(String.format("<td>%s</td>", user.getLogin()));
            writer.append(String.format("<td>%s</td>", user.getEmail()));
            writer.append(String.format("<td>%s</td>", user.getCreatedate()));
            writer.append("</tr>");
        }
        writer.append("</table>");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("do POST:");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        if (!id.isEmpty() && !name.isEmpty() && !login.isEmpty() && !email.isEmpty()) {
            users.updateUser(id, new User(name, login, email, new Date()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("do PUT:");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        if (!name.isEmpty() && !login.isEmpty() && !email.isEmpty()) {
            users.addUser(new User(name, login, email, new Date()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("do DELETE:");
        String id = req.getParameter("id");
        if (!id.isEmpty()) {
            LOG.info(String.format("id= %s", id));
            users.delUser(id);
        }
    }
}
