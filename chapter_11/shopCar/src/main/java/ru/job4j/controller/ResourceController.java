package ru.job4j.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Resource Controller.
 */
public class ResourceController extends HttpServlet {
    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger("ResourceController.class");
    /**
     * Resource path.
     */
    private static final String RESOURCE_PATH = "/WEB-INF/resources";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String url = RESOURCE_PATH + req.getPathInfo();
        RequestDispatcher dispatcher = req.getRequestDispatcher(url);
        try {
            dispatcher.include(req, resp);
        } catch (FileNotFoundException e) {
            LOG.info("file not found: " + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}