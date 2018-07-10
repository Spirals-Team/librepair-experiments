package ru.job4j.crud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User's servlet
 */
public class UsersServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(UsersServlet.class);

    private static final String NAME = "name";
    private static final String LOGIN = "login";
    private static final String EMAIL = "email";
    private static final String CONTENT_TYPE = "text/html";

    private final UserStore users = UserStore.getInstance();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        resp.setContentType(CONTENT_TYPE);
        try (PrintWriter writer = new PrintWriter(resp.getOutputStream())) {
            StringBuilder sb = new StringBuilder().append("<ul>");
            for (User user : users.getAll()) {
                sb.append("<li>").append(user).append("</li>");
            }
            sb.append("</ul>");
            writer.append(sb.toString());
            writer.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) {
        resp.setContentType(CONTENT_TYPE);
        String name = req.getParameter(NAME);
        String login = req.getParameter(LOGIN);
        String email = req.getParameter(EMAIL);
        users.addUser(new User(name, login, email));
    }

    @Override
    protected void doPut(final HttpServletRequest req, final HttpServletResponse resp) {
        resp.setContentType(CONTENT_TYPE);
        String id = req.getParameter("id");
        String name = req.getParameter(NAME);
        String login = req.getParameter(LOGIN);
        String email = req.getParameter(EMAIL);
        users.replace(id, new User(name, login, email));
    }

    @Override
    protected void doDelete(final HttpServletRequest req, final HttpServletResponse resp) {
        resp.setContentType(CONTENT_TYPE);
        users.delete(req.getParameter(EMAIL));
    }
}
