package ru.job4j.todolist;

import ru.job4j.models.Item;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class AddServlet extends HttpServlet {
    private final EnumSingleton items = EnumSingleton.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        items.addOrUpadateItem(new Item(req.getParameter("desc")));
    }
}
