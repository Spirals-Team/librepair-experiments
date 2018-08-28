package ru.job4j.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddressController extends HttpServlet {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        MAPPER.writeValue(writer, DbStore.getInstance().findAllCountries());
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        MAPPER.writeValue(writer, DbStore.getInstance().findCitiesByCountry(req.getParameter("country")));
        writer.flush();
    }
}
