package ru.job4j.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.model.User;
import ru.job4j.model.UserBuilder;
import ru.job4j.service.Validate;
import ru.job4j.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 13.06.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class UserServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserServlet.class);
    private final Validate logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logic.findAllValidate();
        resp.setContentType("text/html;");
        PrintWriter printWriter = new PrintWriter(resp.getOutputStream());
        for (User user : logic.findAllValidate()) {
            printWriter.append(user.toString());
        }
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter printWriter = new PrintWriter(resp.getOutputStream());

        if (req.getParameter("action").equals("add")) {
            User user = new UserBuilder()
                    .setName(req.getParameter("name"))
                    .setLogin(req.getParameter("login"))
                    .setEmail(req.getParameter("email")).build();
            if (logic.addValidate(user)) {
                printWriter.append("The user has been added successfully!");
            } else {
                printWriter.append("A user with this name exists!");
            }
        } else if (req.getParameter("action").equals("update")) {
            User user = new UserBuilder()
                    .setName(req.getParameter("name"))
                    .setLogin(req.getParameter("login"))
                    .setEmail(req.getParameter("email")).build();
            if (logic.updateValidate(user)) {
                printWriter.append("The user is successfully updated!");
            } else {
                printWriter.append("User with this id is not found!");
            }
        } else if (req.getParameter("action").equals("delete")) {
            if (logic.deleteValidate(Integer.parseInt(req.getParameter("id")))) {
                printWriter.append("User successfully removed!");
            } else {
                printWriter.append("User with this id is not found!");
            }
        }
        printWriter.flush();
    }

    @Override
    public void destroy() {
        logic.close();
    }
}
