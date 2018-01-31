package ru.javawebinar.topjava.web.meal;

import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.repository.MealListSorting;
import ru.javawebinar.topjava.web.SpringContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** @author danis.tazeev@gmail.com */
public final class ListMealServlet extends HttpServlet {
    private static final String ATTR_FILTERING_PARAMS = "filteringParams";
    private final MealRestController ctlr = SpringContext.get().getBean(MealRestController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession sess = req.getSession();
        MealListFilteringParams filteringParams = (MealListFilteringParams)sess.getAttribute(ATTR_FILTERING_PARAMS);
        if (filteringParams == null) {
            filteringParams = MealListFilteringParams.noParams();
        }
        try {
            MealListSorting sorting = MealListSorting.valueOf(req.getParameter("sorting")); // NPE, IAE
            req.setAttribute("sorting", sorting.getReversed());
            req.setAttribute(ATTR_FILTERING_PARAMS, filteringParams);
            req.setAttribute("meals", ctlr.list(sorting, filteringParams.getPredicate()));
            req.getRequestDispatcher("/meal-list.jsp").forward(req, res);
        } catch (RuntimeException ex) {
            LoggerFactory.getLogger(ListMealServlet.class).warn("doGet", ex);
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            req.getSession().setAttribute(ATTR_FILTERING_PARAMS, MealListFilteringParams.from(req));
            doGet(req, res);
        } catch (RuntimeException ex) {
            LoggerFactory.getLogger(ListMealServlet.class).warn("doPost", ex);
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static String toString(LocalDateTime dt) {
        return dt.format(DATE_TIME_FORMATTER);
    }
}
