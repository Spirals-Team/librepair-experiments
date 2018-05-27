package ru.job4j.carsale;

import ru.job4j.models.Advert;
import ru.job4j.models.Brand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ServletMainPage extends HttpServlet {
    private final CarStorage carStorage = CarStorage.INSTANCE;

    @Override
    public void init() throws ServletException {
        carStorage.start();
        new DefaultValue().addRoot();
        new DefaultValue().addBrand();
        new DefaultValue().addModel();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filter = req.getParameter("filter");
        String idBrand = req.getParameter("selBrand");
        if (filter != null && !filter.isEmpty()) {
            if (filter.equals("day")) {
                req.setAttribute("Adverts", carStorage.getAdvertDay());
            } else if (filter.equals("pic")) {
                req.setAttribute("Adverts", getAdvertsPic(carStorage.getActivAdvert()));
            } else if (filter.equals("brand")) {
                req.setAttribute("Adverts", carStorage.getAdvertBrand(Integer.parseInt(idBrand)));
            } else if (filter.equals("all")) {
                req.setAttribute("Adverts", carStorage.getActivAdvert());
            }
        } else {
            req.setAttribute("Adverts", carStorage.getActivAdvert());
        }
        req.setAttribute("Brands", carStorage.getList(Brand.class.getSimpleName()));
        req.getRequestDispatcher("/WEB-INF/views/MainPage.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        carStorage.finish();
    }

    private List<Advert> getAdvertsPic(List<Advert> list) {
        List<Advert> newList = new ArrayList<Advert>();
        for (Advert advert : list) {
            if (advert.getPicture().length > 0) {
                newList.add(advert);
            }
        }
        return newList;
    }
}
