package ru.job4j.hibernate.controllers;

import com.google.gson.Gson;
import ru.job4j.hibernate.dao.ItemDaoImpl;
import ru.job4j.hibernate.models.Item;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * User's servlet
 */
public class ListItemsController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        List<Item> all = new ItemDaoImpl().findAll();
        resp.setContentType("text/json");
        PrintWriter wr = new PrintWriter(resp.getOutputStream());
        wr.append(new Gson().toJson(all));
        wr.flush();
    }
}
