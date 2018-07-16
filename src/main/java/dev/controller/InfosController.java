package dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/infos")
public class InfosController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("infos");
        mv.addObject("heure", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return mv;
    }

}
