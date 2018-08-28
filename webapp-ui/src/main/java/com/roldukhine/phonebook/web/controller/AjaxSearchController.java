package com.roldukhine.phonebook.web.controller;

import com.roldukhine.api.EmployeeDao;
import com.roldukhine.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class AjaxSearchController {

    private final EmployeeDao employeeDao;

    @Autowired
    public AjaxSearchController(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @RequestMapping(value = {"/ajax/search"}, method = RequestMethod.POST)
    @ResponseBody
    public List<String> getCity(@RequestParam("query") String search) {
        logger.debug("getCity, search query {}", search);

        List<Employee> employeeList = findEmployeeList(search);

        List<String> employees = employeeList.stream()
                .map(Employee::getLastName)
                .collect(Collectors.toList());

        logger.debug("return employees {}", employees);

        return employees;
    }

    private List<Employee> findEmployeeList(String search) {
        List<Employee> all = employeeDao.getAll();

        if (search == null) {
            return all;
        }

        return all.stream()
                    .filter(it -> it.getLastName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
    }
}
