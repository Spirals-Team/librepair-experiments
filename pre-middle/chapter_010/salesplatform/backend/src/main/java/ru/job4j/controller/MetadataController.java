package ru.job4j.controller;

import com.google.gson.Gson;
import ru.job4j.dao.MetadataStore;
import ru.job4j.model.Metadata;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MetadataController extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        Metadata data = new MetadataStore().getMetadata();
        resp.setContentType("text/json");
        PrintWriter wr = new PrintWriter(resp.getOutputStream());
        wr.append(new Gson().toJson(data));
        wr.flush();
    }
}
