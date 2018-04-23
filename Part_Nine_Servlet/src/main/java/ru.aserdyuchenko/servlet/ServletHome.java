package ru.aserdyuchenko.servlet;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.aserdyuchenko.storage.DataSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class ServletHome extends HttpServlet {
    private static final Logger log = Logger.getLogger(ServletHome.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            request.getRequestDispatcher("/menu.html").forward(request, response);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            BasicConfigurator.configure();
            log.setLevel(Level.INFO);
            response.setContentType("text/html");
            HttpSession session = request.getSession();
            synchronized (session) {
                log.info(DataSource.getInstance().getMap().toString());
                log.info(session.getAttribute("login"));
                request.setAttribute("role", DataSource.getInstance().getMap().get(session.getAttribute("login")).getRole());
            }
            request.getRequestDispatcher("/WEB-INF/views/homeUser.jsp").forward(request, response);
        } catch (Exception e) {
            log.info(e.toString());
            e.printStackTrace();
        }
    }
}
