package ru.job4j.shop.controller.security;

import com.google.gson.Gson;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.shop.model.User;
import ru.job4j.shop.service.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 13.02.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class Register extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(Register.class);
    private DBService service = DBService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = "";
        String login = req.getParameter("reg_login");
        String password = req.getParameter("reg_pass");
        if (login.isEmpty() || password.isEmpty()) {
            result = "Заполните все поля.";
        } else if (!service.checkLogin(login)) {
            result = "Введенные данные не верны. Данный логин занят!";
        } else {
            User user = new User(login, password);
            boolean e = service.addNewUser(user);
            result = String.valueOf(e);
        }
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
        pw.append(new Gson().toJson(result));
        pw.flush();
    }

    @Override
    public void destroy() {
        super.destroy();
        service.close();
    }
}
