package ru.javawebinar.topjava.web.meal;

import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.NotFoundException;
import ru.javawebinar.topjava.web.AuthenticatedUser;
import ru.javawebinar.topjava.web.SpringContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/** @author danis.tazeev@gmail.com */
public final class SaveMealServlet extends HttpServlet {
    private final MealRestController ctlr = SpringContext.get().getBean(MealRestController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            Meal meal = id == null
                    ? new Meal(AuthenticatedUser.getUser(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0)
                    : ctlr.get(Integer.parseInt(id));
            req.setAttribute("sorting", req.getParameter("sorting"));
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("/meal-save.jsp").forward(req, res);
        } catch (RuntimeException | NotFoundException ex) {
            LoggerFactory.getLogger(SaveMealServlet.class).warn("doPost", ex);
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            Meal meal = new Meal(
                    AuthenticatedUser.getUser(),
                    LocalDateTime.parse(req.getParameter("when")),
                    req.getParameter("desc"),
                    Integer.parseInt(req.getParameter("calories")));
            int id = Integer.parseInt(req.getParameter("id"));
            if (id != 0) {
                meal.setId(id);
            }
            ctlr.save(meal);
            res.sendRedirect(req.getContextPath() + "/meal?sorting=" + req.getParameter("sorting"));
        } catch (RuntimeException | NotFoundException ex) {
            LoggerFactory.getLogger(SaveMealServlet.class).warn("doPost", ex);
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
