package ru.job4j.shop.controller;

import com.google.gson.Gson;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.shop.model.Product;
import ru.job4j.shop.service.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 04.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class View extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(View.class);
    private DBService service = DBService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Product product = service.getProduct(id);
        String result = new Gson().toJson(product);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
        pw.append(result);
        pw.flush();
    }
}
