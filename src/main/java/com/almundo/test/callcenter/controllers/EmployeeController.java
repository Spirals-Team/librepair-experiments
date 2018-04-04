package com.almundo.test.callcenter.controllers;

import com.almundo.test.callcenter.entities.Employee;
import com.almundo.test.callcenter.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController extends BaseController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Employee>> getAll() {
        List<Employee> employees = employeeService.getAll();
        return new ResponseEntity(employees, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Employee> get(@PathVariable(value = "id") Long id) {
        Employee employee = employeeService.get(id);
        return new ResponseEntity(employee, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Employee> createRule(@RequestBody Employee employee) {
		Employee employeeCreated = employeeService.save(employee);
		return new ResponseEntity(employeeCreated, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Employee> updateRule(@PathVariable("id") Long id, @RequestBody Employee employee) {
        Employee employeeUpdated = employeeService.update(id, employee);
		return new ResponseEntity(employeeUpdated, HttpStatus.OK);
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Employee> delete(@PathVariable("id") Long id) {
        Employee employee = employeeService.delete(id);
        return new ResponseEntity(employee, HttpStatus.OK);
    }
}
