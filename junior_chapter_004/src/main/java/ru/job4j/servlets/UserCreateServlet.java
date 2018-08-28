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
import java.util.Calendar;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 21.06.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class UserCreateServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserCreateServlet.class);
    private Validate logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String countries = req.getParameter("countries");
        String citi = req.getParameter("citi");
        User newUser = new UserBuilder()
                .setName(name)
                .setLogin(login)
                .setEmail(email)
                .setCreateDate(Calendar.getInstance())
                .setPassword(password)
                .setCountries(countries)
                .setCiti(citi)
                .build();
        logic.addValidate(newUser);
        resp.sendRedirect(String.format("%s/list", req.getContextPath()));
    }

    @Override
    public void destroy() {
        logic.close();
    }
}
