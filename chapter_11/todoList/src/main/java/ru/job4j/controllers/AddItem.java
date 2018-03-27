package ru.job4j.controllers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.service.DbService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 11.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class AddItem extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(AddItem.class);
    private DbService dbService = DbService.getInstance();

    @Override
    public void destroy() {
        super.destroy();
        dbService.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String description = req.getParameter("item");
        dbService.add(description);
    }
}
