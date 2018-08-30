package ru.job4j.userapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Signout servlet.
 */
public class SignoutController extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        req.getSession().invalidate();
        resp.sendRedirect(String.format("%s/", req.getContextPath()));
    }
}
