package ru.job4j.carsale;

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
public class ServletGetListModels extends HttpServlet {
    private final CarStorage carStorage = CarStorage.INSTANCE;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            resp.setContentType("text/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, carStorage.getModels(Integer.parseInt(id)));
            writer.flush();
        }
    }
}
