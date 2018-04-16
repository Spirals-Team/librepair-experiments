package com.gdc.aerodev.web.controllers;

import com.gdc.aerodev.web.logging.LoggingWeb;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController implements LoggingWeb{

    @RequestMapping(method = RequestMethod.GET, path = "/login")
    public String getLogin(){
        return "login";
    }
}
