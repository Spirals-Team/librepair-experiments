package ru.job4j.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 25.07.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class SignoutController extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(SignoutController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect(String.format("%s/signin", req.getContextPath()));
    }
}
