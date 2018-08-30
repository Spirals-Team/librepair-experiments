package ru.job4j.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Index controller.
 * Return static resources like index.html
 */
@Controller
public class IndexController {

    @RequestMapping({"", "/"})
    public String getIndexPage(Model model) {
        return "public/index.html";
    }
}
