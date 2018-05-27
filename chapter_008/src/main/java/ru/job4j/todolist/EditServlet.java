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
public class EditServlet extends HttpServlet {
    private final EnumSingleton items = EnumSingleton.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Item item = items.getItem(id);
        if (item != null) {
            item.setDone(!item.isDone());
            items.addOrUpadateItem(item);
        }
    }
}
