package ru.javawebinar.topjava.web.user;

import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.repository.MealListSorting;
import ru.javawebinar.topjava.repository.NotFoundException;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.web.AuthenticatedUser;
import ru.javawebinar.topjava.web.SpringContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class LoginServlet extends HttpServlet {
    private final UserRepository repo = SpringContext.get().getBean(UserRepository.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("users", repo.list());
        req.getRequestDispatcher("/login.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            AuthenticatedUser.setUser(repo.get(id));
            res.sendRedirect(req.getContextPath() + "/meal?sorting=" + MealListSorting.DESC);
        } catch (RuntimeException | NotFoundException ex) {
            LoggerFactory.getLogger(LoginServlet.class).warn("doPost", ex);
            doGet(req, res);
        }
    }
}
