package ru.job4j.shop.controller.cart;

import com.google.gson.Gson;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.shop.service.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 01.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class AddToCart extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(AddToCart.class);
    private DBService service = DBService.getInstance();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result;
        if (req.getSession().getAttribute("login") != null) {
            String id = req.getParameter("id");
            String login = (String) req.getSession().getAttribute("login");
            service.addProductToCart(login, id);
            result = "0";
        } else {
            System.out.println("no login");
            result = "Вам необходимо авторизироваться.";
        }
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
        pw.append(new Gson().toJson(result));
        pw.flush();
    }

    @Override
    public void destroy() {
        super.destroy();
        service.close();
    }
}
