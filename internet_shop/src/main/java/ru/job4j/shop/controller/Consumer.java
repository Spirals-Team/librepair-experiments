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

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 07.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class Consumer extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("admin") != null) {
            User user = service.getUser(req.getParameter("id"));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
            pw.append(new Gson().toJson(user));
            pw.flush();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        service.close();
    }

    private static final Logger LOG = LogManager.getLogger(Consumer.class);
    private DBService service = DBService.getInstance();
}
