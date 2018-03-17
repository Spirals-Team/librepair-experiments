package ru.job4j.controllers;

import com.google.gson.Gson;
import ru.job4j.model.Item;
import ru.job4j.service.DbService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 08.03.18;
 * @version $Id$
 * @since 0.1
 */
public class ItemController extends HttpServlet {
    private DbService dbService = DbService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String status = req.getParameter("status");
        List<Item> items = dbService.getItems(status);
        String json = new Gson().toJson(items);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
        pw.append(json);
        pw.flush();
    }

    @Override
    public void destroy() {
        super.destroy();
        dbService.close();
    }
}
