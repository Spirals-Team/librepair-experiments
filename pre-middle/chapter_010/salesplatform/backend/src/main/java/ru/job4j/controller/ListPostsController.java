package ru.job4j.controller;

import com.google.gson.Gson;
import ru.job4j.dao.PostDaoImpl;
import ru.job4j.model.Post;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ListPostsController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        List<Post> all = new PostDaoImpl().findAll();
        // TODOset password to empty
        resp.setContentType("text/json");
        PrintWriter wr = new PrintWriter(resp.getOutputStream());
        wr.append(new Gson().toJson(all));
        wr.flush();
    }
}
