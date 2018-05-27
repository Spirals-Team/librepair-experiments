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
public class ServletEditStatusAdvert extends HttpServlet {
    private final CarStorage carStorage = CarStorage.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Advert advert = carStorage.getAdvert(Integer.parseInt(id));
        advert.setStatus(!advert.isStatus());
        carStorage.addObject(advert);
        resp.sendRedirect("useradvert");
    }
}
