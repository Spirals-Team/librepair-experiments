package ru.job4j.todolist;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class JsonController extends HttpServlet {
    private final EnumSingleton items = EnumSingleton.INSTANCE;

    @Override
    public void init() throws ServletException {
        items.start();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, items.getList());
        writer.flush();
    }

    public void destroy() {
        items.finish();
    }
}
