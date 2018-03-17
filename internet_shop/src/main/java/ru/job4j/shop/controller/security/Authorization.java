package ru.job4j.shop.controller.security;

import com.google.gson.Gson;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.shop.service.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 26.02.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class Authorization extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(Authorization.class);
    private DBService service = DBService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
        String type = req.getParameter("aut");
        if ("login_pass".equals(type)) {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            String remember = req.getParameter("remember");
            boolean present = service.checkUser(login, password);
            if (present) {
                HttpSession session = req.getSession();
                session.setAttribute("login", login);
                if ("yes".equals(remember)) {
                    Cookie cookLogin = new Cookie("login", login);
                    cookLogin.setMaxAge(31 * 24 * 60 * 60);
                    resp.addCookie(cookLogin);
                }
            }
            String result = new Gson().toJson(present);
            pw.append(result);
            pw.flush();
        }
        if ("cook".equals(type)) {
            Cookie[] cookies = req.getCookies();
            String login = null;
            for (int i = 0; i < cookies.length; i++) {
                if ("login".equals(cookies[i].getName())) {
                   String loginCook = cookies[i].getValue();
                    if (!loginCook.isEmpty()) {
                        if (!service.checkLogin(loginCook)) {
                            System.out.println("login present");
                            HttpSession session = req.getSession();
                            session.setAttribute("login", loginCook);
                            login = loginCook;
                            break;
                        }
                    }
                }
            }
            pw.append(new Gson().toJson(login));
            pw.flush();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String out = req.getParameter("session");
        if ("logOut".equals(out)) {
            req.getSession().invalidate();
        } else if ("exist".equals(out)) {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
            pw.append(new Gson().toJson(req.getSession().getAttribute("login")));
            pw.flush();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        service.close();
    }
}
