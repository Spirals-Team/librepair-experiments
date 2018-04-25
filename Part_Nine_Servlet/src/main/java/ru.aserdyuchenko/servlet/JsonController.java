package ru.aserdyuchenko.servlet;

import org.json.JSONObject;
import ru.aserdyuchenko.storage.DataSource;
import ru.aserdyuchenko.storage.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JsonController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());

        try {
            JSONObject jsonUser = new JSONObject();
            DataSource source = new DataSource();
            List<String> list = new ArrayList();
            for (User user : source.getList()) {
                jsonUser.put("login", user.getLogin())
                        .put("name", user.getName())
                        .put("email", user.getEmail())
                        .put("createDate", user.getCreateDate())
                        .put("password", user.getPassword())
                        .put("role", user.getRole())
                        .put("country", user.getCountry())
                        .put("city", user.getCity());
                list.add(jsonUser.toString());
            }
            writer.append(list.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            writer.flush();
        }
    }
}
