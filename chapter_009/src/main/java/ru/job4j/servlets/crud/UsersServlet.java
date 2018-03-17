package ru.job4j.servlets.crud;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.servlets.application.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 08.01.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class UsersServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UsersServlet.class);
    private final UserStore userStore = UserStore.getInstance();
    private User user;
    /**
     * обновление данных пользователя.
     * @param req запрос.
     * @param resp ответ.
     * @throws ServletException ex.
     * @throws IOException ex.
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        this.user = new User();
        String oldLogin = req.getParameter("login");
        this.user.setName(req.getParameter("newName"));
        this.user.setLogin(req.getParameter("newLogin"));
        this.user.setEmail(req.getParameter("newEmail"));
        int i = this.userStore.updateUser(oldLogin, user);
        PrintWriter pw = new PrintWriter(resp.getOutputStream());
        if (i > 0) {
            pw.append("User was update successful.");
        } else {
            pw.append("User who's this login das not exist.");
        }
        pw.flush();
        this.user = null;
    }

    /**
     * Удаление пользователя.
     * @param req запрос.
     * @param resp ответ.
     * @throws ServletException ex.
     * @throws IOException ex.
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String login = req.getParameter("login");
        int i = this.userStore.deleteUser(login);
        PrintWriter pw = new PrintWriter(resp.getOutputStream());
        if (i > 0) {
            pw.append("User was delete successful.");
        } else {
            pw.append("User who's this login das not exist.");
        }
        pw.flush();
    }

    /**
     * Получение пользователя по имени.
     * @param req запрос.
     * @param resp ответ.
     * @throws ServletException ex.
     * @throws IOException ex.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String login = req.getParameter("login");
        if (login != null) {
            this.user = this.userStore.getUserByLogin(login);
        }
        PrintWriter printWriter = new PrintWriter(resp.getOutputStream());
        if (this.user != null) {
            printWriter.append(user.toString());
        } else {
            printWriter.append("User with this login does not exist");
        }
        printWriter.flush();
        this.user = null;
    }

    /**
     * Добавление нового пользователя.
     * @param req запрос к серверу.
     * @param resp ответ сервера.
     * @throws ServletException еx.
     * @throws IOException еx.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        this.user = new User();
        this.user.setLogin(req.getParameter("login"));
        this.user.setName(req.getParameter("name"));
        this.user.setEmail(req.getParameter("email"));
        this.user.setCreateDate(Calendar.getInstance());
        userStore.addNewUser(this.user);
        this.user = null;
        PrintWriter pw = new PrintWriter(resp.getOutputStream());
        pw.append("User was saved successful");
        pw.flush();
    }

    @Override
    public void destroy() {
        this.userStore.close();
    }
}
