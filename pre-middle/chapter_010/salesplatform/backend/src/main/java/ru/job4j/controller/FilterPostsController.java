package ru.job4j.controller;

import com.google.gson.Gson;
import ru.job4j.DispatchFilter;
import ru.job4j.Filter;
import ru.job4j.model.Post;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FilterPostsController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<String> queryParams = new ArrayList<>(req.getParameterMap().keySet());
        String condition = queryParams.get(0);
        Filter filter = Filter.filterFromName(condition);
        List<Post> posts = new DispatchFilter().init().sent(filter, req.getParameter(condition));
        resp.setContentType("text/json");
        PrintWriter wr = new PrintWriter(resp.getOutputStream());
        wr.append(new Gson().toJson(posts));
        wr.flush();
    }
}
