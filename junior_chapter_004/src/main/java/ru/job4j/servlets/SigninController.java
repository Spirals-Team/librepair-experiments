package ru.job4j.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.service.Validate;
import ru.job4j.service.ValidateService;
import ru.job4j.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 24.07.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class SigninController extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(SigninController.class);
    private Validate logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/LoginView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (logic.isCredentional(login, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("login", login);
            resp.sendRedirect(String.format("%s/list", req.getContextPath()));
        } else {
            req.setAttribute("error", "Credentional invalid");
            doGet(req, resp);
        }

    }
}
