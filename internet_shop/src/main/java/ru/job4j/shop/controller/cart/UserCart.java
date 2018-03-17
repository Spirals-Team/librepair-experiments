package ru.job4j.shop.controller.cart;

import com.google.gson.Gson;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
 * @author Hincu Andrei (andreih1981@gmail.com)on 02.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class UserCart extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(UserCart.class);
    private DBService service = DBService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String login = (String) req.getSession().getAttribute("login");
        if ("showCart".equals(action)) {
            List<Purchase> purchases = service.getUserCart(login);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
            pw.append(new Gson().toJson(purchases));
            pw.flush();
        }
        if ("deleteAll".equals(action)) {
            service.deleteUserCart(login);
        }
        if ("deleteProduct".equals(action)) {
            String idProduct = req.getParameter("id");
            service.deleteProductFromCart(idProduct);
        }
        if ("increment".equals(action)) {
            String id = req.getParameter("id");
            service.incrementCountOfProducts(id);
        }
        if ("discrement".equals(action)) {
            String id = req.getParameter("id");
            service.dicrementCountOfProducts(id);
        }
        if ("setCount".equals(action)) {
            String count = req.getParameter("count");
            String id = req.getParameter("cart_id");
            try {
                Integer coun = Integer.parseInt(count);
                service.setCountOfProductsInCart(id, coun);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String result = service.translationInToSold((String) req.getSession().getAttribute("login"));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
        pw.append(result);
        pw.flush();
    }

    @Override
    public void destroy() {
        super.destroy();
        service.close();
    }
}
