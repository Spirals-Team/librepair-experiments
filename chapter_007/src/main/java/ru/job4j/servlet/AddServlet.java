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
public class AddServlet extends HttpServlet {
    private final UserStore users = UserStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        if (name != null && !name.equals("")
                && login != null && !login.equals("")
                && email != null && !email.equals("")) {
            users.addUser(new User(name, login, email, new Date()));
            resp.sendRedirect(req.getContextPath().concat("/u"));
        } else {
            this.doPost(req, resp);
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
        writer.append("<br><form action='" + req.getContextPath() + "/add' method='get'>");
        writer.append("<table><tr>");
        writer.append("<td>Name: <input type=text name='name'/></td>");
        writer.append("<td>Login: <input type=text name='login'/></td>");
        writer.append("<td>E-mail: <input type=text name='email'/></td>");
        writer.append("<td><input type='submit' value='add'></td></form>");
        writer.append("</tr></table>");
        writer.append("<br><form action='" + req.getContextPath() + "/u' method='post'>");
        writer.append("<input type='submit' value='back'></form>");
        writer.append("</body>");
        writer.append("</html>");
        writer.flush();


    }
}
