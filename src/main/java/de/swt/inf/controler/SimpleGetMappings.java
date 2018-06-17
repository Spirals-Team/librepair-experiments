package de.swt.inf.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class SimpleGetMappings {


    @RequestMapping(value = "/about")
    public String about() {
        //diese methode zeigt die about.html seite an
        return "about";
    }

    @RequestMapping(value = "/greeting")
    public String greeting(HttpServletRequest request, Model model) {
        model.addAttribute("name", request.getParameter("name"));
        return "greeting";
    }

    @RequestMapping(value="/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value="/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for(int i=0; i< cookies.length; i++) {
            if(cookies[i].getName().equals("login")){
                cookies[i].setMaxAge(0);
                response.addCookie(cookies[i]);
                System.out.println("Successfully logged out!");
                try {
                    response.sendRedirect("/login");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
