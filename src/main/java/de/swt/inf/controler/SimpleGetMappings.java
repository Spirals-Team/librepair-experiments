package de.swt.inf.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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
}
