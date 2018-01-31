package ru.javawebinar.topjava.web.meal;

import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.repository.NotFoundException;
import ru.javawebinar.topjava.web.SpringContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** @author danis.tazeev@gmail.com */
public final class DeleteMealServlet extends HttpServlet {
    private final MealRestController ctlr = SpringContext.get().getBean(MealRestController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            ctlr.delete(Integer.parseInt(req.getParameter("id")));
            res.sendRedirect(req.getContextPath() + "/meal?sorting=" + req.getParameter("sorting"));
        } catch (RuntimeException | NotFoundException ex) {
            LoggerFactory.getLogger(DeleteMealServlet.class).warn(ex.toString());
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
