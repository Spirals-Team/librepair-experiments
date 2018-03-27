package ru.job4j.shop.controller;

import com.google.gson.Gson;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.shop.model.Product;
import ru.job4j.shop.model.Purchase;
import ru.job4j.shop.service.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 07.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class Orders extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(Orders.class);
    private DBService service = DBService.getInstance();

    @Override
    public void destroy() {
        super.destroy();
        service.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("admin") != null) {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
            String id = req.getParameter("id");
            if (id != null) {
                Product product = service.getOrderProduct(id);
                pw.append(new Gson().toJson(product));
                pw.flush();
            } else {
                List<Purchase> list = service.getOrders();
                pw.append(new Gson().toJson(list));
                pw.flush();
            }
        }
    }
}
