package ua.epam.spring.hometask.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Vladimir_Vysokomorny on 04-Dec-17.
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ModelAndView allErrors(Exception e) {

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", e);
        return mav;
    }
}
