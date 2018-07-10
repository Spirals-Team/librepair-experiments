package ru.job4j.hibernate.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.job4j.hibernate.dao.ItemDaoImpl;
import ru.job4j.hibernate.models.Item;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Edit Item controller.
 */
public class EditItemController extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        String body = req.getReader().lines().sequential()
            .reduce(System.lineSeparator(), (accumulator, actual)
                -> accumulator + actual);
        List<Item> items = new Gson().fromJson(body,
            new TypeToken<List<Item>>() {
            }.getType());
        new ItemDaoImpl().updateAll(items);
    }
}
