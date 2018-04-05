package com.almundo.test.callcenter.services;

import com.almundo.test.callcenter.entities.Employee;
import com.almundo.test.callcenter.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee employee) {
        Employee employeeSaved = employeeRepository.save(employee);
        return employeeSaved;
    }

    public Employee update(Long id, Employee employee) {
        if (id == employee.getId()) {
            employee = employeeRepository.save(employee);
        }

        return employee;
    }

    public Employee get(Long id) {
        Employee employee = employeeRepository.getOne(id);
        return employee;
    }

    public Employee delete(Long id) {
        Employee toDelete = get(id);
        employeeRepository.delete(toDelete);

        return toDelete;
    }

    public List<Employee> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    public List<Employee> getAllAvailables() {
        List<Employee> employees = employeeRepository.findAllAvailables();
        return employees;
    }

    public List<Employee> getAllFrees() {
        List<Employee> employees = employeeRepository.findAllFrees();
        return employees;
    }

    public List<Employee> getAllFreesByType(Employee.Type type) {
        List<Employee> employees = employeeRepository.findAllFreesByType(type);
        return employees;
    }

    public List<Employee> getAllThatCanAttendACall() {
        List<Employee> employees = getAllFreesByType(Employee.Type.OPERATOR);

        if (employees.isEmpty()) {
            employees = getAllFreesByType(Employee.Type.SUPERVISOR);
        }

        if (employees.isEmpty()) {
            employees = getAllFreesByType(Employee.Type.MANAGER);
        }

        return employees;
    }
}
