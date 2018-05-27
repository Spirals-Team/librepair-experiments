package ru.job4j.carsale;

import ru.job4j.models.Advert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ServletViewUserAdvertPage extends HttpServlet {
    private final CarStorage carStorage = CarStorage.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/ViewUserAdvertPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Advert advert = carStorage.getAdvert(Integer.parseInt(id));
        carStorage.delObject(advert);
        doGet(req, resp);
    }
}
