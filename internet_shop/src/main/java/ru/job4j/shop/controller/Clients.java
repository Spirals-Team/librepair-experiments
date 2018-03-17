package ru.job4j.shop.controller;

import com.google.gson.Gson;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.shop.model.User;
import ru.job4j.shop.service.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 27.02.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class Clients extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(Clients.class);
    private DBService service  = DBService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("admin") != null) {
            if ("show".equals(req.getParameter("action"))) {
                List<User> users = service.getAllUsers();
                String jason = new Gson().toJson(users);
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
                pw.append(jason);
                pw.flush();
            }
            if ("delete".equals(req.getParameter("action"))) {
                String id = req.getParameter("id");
                boolean result = service.deleteUser(id);
                String jason = new Gson().toJson(result);
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
                pw.append(jason);
                pw.flush();
            }
        }
    }
}
