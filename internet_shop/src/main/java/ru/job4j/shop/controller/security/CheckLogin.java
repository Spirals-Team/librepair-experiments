package ru.job4j.shop.controller.security;

import com.google.gson.Gson;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.shop.service.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 19.02.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class CheckLogin extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(CheckLogin.class);
    private DBService service = DBService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
        char[]chars = {'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'r', 's',
                't', 'u', 'v', 'x', 'y', 'z',
                '1', '2', '3', '4', '5', '6',
                '7', '8', '9', '0'};
        char[]result = new char[7];
        for (int i = 0; i < result.length; i++) {
            result[i] = chars[(int) (Math.random() * chars.length)];
        }
        pw.append(new String(result));
        pw.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("reg_login");
        boolean checkLogin = service.checkLogin(login);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
        String result = new Gson().toJson(checkLogin);
        pw.append(result);
        pw.flush();
    }

    @Override
    public void destroy() {
        super.destroy();
        service.close();
    }
}
