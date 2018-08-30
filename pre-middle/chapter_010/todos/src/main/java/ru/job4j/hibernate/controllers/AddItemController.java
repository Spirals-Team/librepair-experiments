package ru.job4j.hibernate.controllers;

import com.google.gson.Gson;
import ru.job4j.hibernate.dao.ItemDaoImpl;
import ru.job4j.hibernate.models.Item;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Add Item controller.
 */
public class AddItemController extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        String body = req.getReader().lines().sequential()
            .reduce(System.lineSeparator(), (accumulator, actual)
                -> accumulator + actual);
        Item item = new Gson().fromJson(body, Item.class);
        item.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        new ItemDaoImpl().create(item);
    }
}
