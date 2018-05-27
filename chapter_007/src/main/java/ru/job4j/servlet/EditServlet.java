package ru.job4j.servlet;

import ru.job4j.crudservlet.User;
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
public class EditServlet extends HttpServlet {
    private final UserStore users = UserStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        if (id != null && !id.equals("")
                && name != null && !name.equals("")
                && login != null && !login.equals("")
                && email != null && !email.equals("")) {
            users.updateUser(id, new User(name, login, email, new Date()));
            resp.sendRedirect(req.getContextPath().concat("/u"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append("<!DOCTYPE html>"
                + "<html lang='en'>"
                + "<head>"
                + "<meta charset='UTF-8'>"
                + "<title>Edit user</title>"
                + "</head>"
                + "<body>");
        writer.append("<br><form action='" + req.getContextPath() + "/edit' method='get'>");
        writer.append("<input type='hidden' name='id' value='" + req.getParameter("id") + "'/>");
        writer.append("<table><tr>");
        writer.append("<td>Name: <input type=text value='" + req.getParameter("name") + "' name='name'/></td>");
        writer.append("<td>Login: <input type=text value='" + req.getParameter("login") + "' name='login'/></td>");
        writer.append("<td>E-mail: <input type=text value='" + req.getParameter("email") + "' name='email'/></td>");
        writer.append("<td><input type='submit' value='edit'></td></form>");
        writer.append("</tr></table>");
        writer.append("<br><form action='" + req.getContextPath() + "/u' method='post'>");
        writer.append("<input type='submit' value='back'></form>");
        writer.append("</body>");
        writer.append("</html>");
        writer.flush();
    }
}
