package ru.job4j.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.service.Validate;
import ru.job4j.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 22.06.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class UserDeleteServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserDeleteServlet.class);
    private Validate logic = ValidateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logic.deleteValidate(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect(String.format("%s/list", req.getContextPath()));
    }

    @Override
    public void destroy() {
        logic.close();
    }
}
