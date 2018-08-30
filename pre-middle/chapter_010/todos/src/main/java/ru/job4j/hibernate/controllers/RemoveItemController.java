package ru.job4j.hibernate.controllers;

import com.google.gson.Gson;
import ru.job4j.hibernate.dao.ItemDaoImpl;
import ru.job4j.hibernate.models.Item;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Remove Item controller.
 */
public class RemoveItemController extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        String body = req.getReader().lines().sequential()
            .reduce(System.lineSeparator(), (accumulator, actual)
                -> accumulator + actual);
        Item item = new Gson().fromJson(body, Item.class);
        new ItemDaoImpl().delete(item);
    }
}
