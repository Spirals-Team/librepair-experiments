package ru.job4j.servlet;

import org.apache.log4j.Logger;
import ru.job4j.crudservlet.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ShowAllServlet extends HttpServlet {
    private final static Logger LOG = Logger.getLogger(ShowAllServlet.class);
    private final UserStore users = UserStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append("<!DOCTYPE html>"
                + "<html lang='en'>"
                + "<head>"
                + "<meta charset='UTF-8'>"
                + "<title>All users</title>"
                + "</head>"
                + "<body>");
        writer.append("<br><form action='" + req.getContextPath() + "/add' method='post'>");
        writer.append("<input type='submit' value='Add new user'></form>");
        writer.append("<br><table border='1'>");
        writer.append("<tr><th>ID</th><th>Name</th><th>Login</th><th>E-mail</th><th>Time create</th>");
        writer.append("<th>Edit</th><th>Delete</th></tr>");
        for (User user : users.getUsers()) {
            writer.append("<tr>");
            writer.append(String.format("<td>%s</td>", user.getId()));
            writer.append(String.format("<td>%s</td>", user.getName()));
            writer.append(String.format("<td>%s</td>", user.getLogin()));
            writer.append(String.format("<td>%s</td>", user.getEmail()));
            writer.append(String.format("<td>%s</td>", user.getCreatedate()));
            writer.append("<td><form action='" + req.getContextPath() + "/edit' method='post'>");
            writer.append("<input type='hidden' name='id' value='" + user.getId() + "'/>");
            writer.append("<input type='hidden' name='name' value='" + user.getName() + "'/>");
            writer.append("<input type='hidden' name='login' value='" + user.getLogin() + "'/>");
            writer.append("<input type='hidden' name='email' value='" + user.getEmail() + "'/>");
            writer.append("<input type='submit' value='edit'></form></td>");
            writer.append("<td><form action='" + req.getContextPath() + "/del' method='post'>");
            writer.append("<input type='hidden' name='del_id' value='" + user.getId() + "'/>");
            writer.append("<input type='submit' value='delete'></form></td>");
            writer.append("</tr>");
        }
        writer.append("</table>");
        writer.append("<br><form action='" + req.getContextPath() + "/add' method='post'>");
        writer.append("<input type='submit' value='Add new user'></form>");
        writer.append("</body>");
        writer.append("</html>");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
