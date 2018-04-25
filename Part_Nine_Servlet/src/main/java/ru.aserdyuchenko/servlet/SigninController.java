package ru.aserdyuchenko.servlet;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.aserdyuchenko.storage.DataSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class SigninController extends HttpServlet {
    private static final Logger log = Logger.getLogger(ServletHome.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/authorization.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            if (DataSource.getInstance().isCredentional(login, password)) {
                HttpSession session = req.getSession();
                synchronized (session) {
                    log.info(session.getAttribute("login"));
                    session.setAttribute("login", login);
                    if(req.getParameter("exit") != null) {
                        session.invalidate();
                    }
                }
                resp.sendRedirect(String.format("%s/home", req.getContextPath()));
            } else {
                req.setAttribute("error", "Credentional invalid");
                doGet(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
