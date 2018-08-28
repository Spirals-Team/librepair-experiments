package com.roldukhine.phonebook.web.controller;

import com.roldukhine.entity.Employee;
import com.roldukhine.entity.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping(value = "/photo")
public class PhotoEmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public PhotoEmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("employeeId") long employeeId) {
        logger.debug("handleFileUpload file {}, employeeId {}", employeeId);
        if (!file.isEmpty()) {
            try {
                byte[] bytesFromRequestFile = file.getBytes();
                Employee employee = employeeService.getEmployee(employeeId);
                employeeService.updatePhoto(employee, bytesFromRequestFile);
            } catch (Exception e) {
                logger.error("get bytes from multipart file ", e);
            }
        }

        return "redirect:/employee/edit/" + employeeId;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteImageForEmployee(@RequestParam("employeeId") long employeeId) {
        logger.debug("deleteImageForEmployee, employeeId {}", employeeId);
        Employee employee = employeeService.getEmployee(employeeId);
        logger.debug("get employee from service: {}", employee);
        employeeService.updatePhoto(employee, null);

        return "redirect:/employee/edit/" + employeeId;
    }
}
