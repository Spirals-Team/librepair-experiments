package de.swt.inf.controler;

import de.swt.inf.database.DaoFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;


@Controller
public class LoginController {
    public static boolean test = false;
    public static final String MSG_USERNAME_OR_PASSWORD_WRONG = "Benutzername oder Passwort falsch!";
    public boolean login;
    private String username = "";
    private String password = "";
    private String uID = "";

    public boolean loggedIn(){
        Connection con = DaoFactory.getConnection();
        String query = "SELECT * FROM user WHERE USERNAME = '"+this.username+"';";
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                this.uID = (rs.getString("USER_ID"));
                String passwordDB = (rs.getString("PASSWORD"));
                if (passwordDB.equals(this.password)) {
                    System.out.println("Successfully LoggedIn!");
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(MSG_USERNAME_OR_PASSWORD_WRONG);
        return false;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void getLoginData(HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String logName = request.getParameter("LoginName");
        String logPassword = request.getParameter("LoginPassword");
        System.out.println("Username: " + logName);
        System.out.println("Password: " + logPassword);
        this.username = logName;
        this.password = logPassword;
        boolean success = loggedIn();
        try {
            if (success == true && test != true) {
                Cookie ck = new Cookie("login", "" + uID);
                response.addCookie(ck);
                response.sendRedirect("/dashboard/woche");
            } else {
                response.sendRedirect("/login");
            }
            if(test){
                response.sendRedirect("/dashboard");
            }
        } catch (Exception io) {
            io.printStackTrace();
        }
    }


    public static boolean isUserLoggedIn(Cookie[] cookies) {
        if (!test) {
            if(cookies == null) return false;
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("login")) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
