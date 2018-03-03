package com.javapro.costs.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by unlimit1984 on 01-Mar-18.
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
