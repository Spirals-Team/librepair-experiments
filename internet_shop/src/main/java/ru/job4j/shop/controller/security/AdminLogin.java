package ru.job4j.shop.controller.security;

import com.google.gson.Gson;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.shop.service.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 05.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class AdminLogin extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(AdminLogin.class);
    private DBService service = DBService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("exit".equals(req.getParameter("id"))) {
            req.getSession().invalidate();
        }
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
        String result = "";
        if (req.getParameter("session") != null) {
            if (req.getSession().getAttribute("admin") != null) {
                result = new Gson().toJson("exist");
            } else {
                result = new Gson().toJson("notExist");
            }
            pw.append(result);
            pw.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String logon = req.getParameter("input_login");
        String password = req.getParameter("input_pass");
        boolean result = service.checkAdmin(logon, password);
        if (result) {
            HttpSession session = req.getSession();
            session.setAttribute("admin", "admin");
        }
        String json = new Gson().toJson(result);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
        pw.append(json);
        pw.flush();
    }

    @Override
    public void destroy() {
        super.destroy();
        service.close();
    }
}
