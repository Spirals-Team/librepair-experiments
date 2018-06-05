package pl.hycom.ip2018.searchengine.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class MainController {

    @RequestMapping(value = "/", method = GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/analytics", method = GET)
    public String analytics() {
        return "analytics";
    }

    @RequestMapping(value = "/history", method = GET)
    public String history() {
        return "history";
    }

    @RequestMapping(value = "/results", method = GET)
    public String results() {
        return "results";
    }
}
