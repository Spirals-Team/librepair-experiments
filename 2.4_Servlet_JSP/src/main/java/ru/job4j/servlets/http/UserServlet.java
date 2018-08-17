package ru.job4j.servlets.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * Class UserServlet.
 * @author ifedorenko
 * @since 27.07.2018
 */
public class UserServlet extends HttpServlet {
    /**
     * ValidateService
     */
    private final ValidateService service = ValidateService.getInstance();
    /**
     * Map for Consumer
     */
    private Map<String, Consumer<HttpServletRequest>> map = new HashMap<>();
    /**
     * add
     */
    private final String add = "add";
    /**
     * update
     */
    private final String update = "update";
    /**
     * delete
     */
    private final String delete = "delete";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        this.service.findAll().stream()
                .forEach(n -> writer.append(n.toString() + "<br>"));
        writer.flush();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String action = req.getParameter("action").toLowerCase();
        try {
            map.get(action).accept(req);
            doGet(req, resp);
        } catch (NoSuchElementException nsee) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    @Override
    public void init() {
        map.put(add, n -> service.add(n.getParameter("name"),
                n.getParameter("login"),
                n.getParameter("email"),
                n.getParameter("role"),
                n.getParameter("password")));
        map.put(update, n -> service.update(n.getParameter("login"),
                n.getParameter("name"),
                n.getParameter("email"),
                n.getParameter("role"),
                n.getParameter("password")));
        map.put(delete, n -> service.delete(Integer.valueOf(n.getParameter("id"))));
    }
}
