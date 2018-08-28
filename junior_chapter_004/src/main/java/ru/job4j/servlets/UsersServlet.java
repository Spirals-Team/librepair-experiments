package ru.job4j.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.service.Validate;
import ru.job4j.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 21.06.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class UsersServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UsersServlet.class);
    private Validate logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", logic.findAllValidate());
        req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
    }
}
