package com.roldukhine.phonebook.web.controller;

import com.roldukhine.entity.Employee;
import com.roldukhine.entity.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
public class RootController {

    private final EmployeeService employeeService;

    @Autowired
    public RootController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView root() {
        logger.debug("call root mapping");
        List<Employee> employeeList = employeeService.getAll();
        ModelAndView modelAndView = new ModelAndView("index");
        logger.debug("employeeList {}", employeeList);
        modelAndView.addObject("employeeList", employeeList);
        return modelAndView;
    }
}
