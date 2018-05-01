package com.uniovi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador para gestionar la pagina de inicio
 * 
 * @author Tania Alvarez Diaz
 *
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
	return "index";
    }

    @RequestMapping("/home")
    public String home() {
	return "home";
    }
}