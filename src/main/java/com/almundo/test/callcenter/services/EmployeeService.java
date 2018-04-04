package com.almundo.test.callcenter.services;

import com.almundo.test.callcenter.entities.Call;
import com.almundo.test.callcenter.entities.Employee;
import com.almundo.test.callcenter.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService  {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee employee) {
        Employee employeeSaved = employeeRepository.save(employee);
        return employeeSaved;
    }

    public Employee update(Long id, Employee employee) {
        Employee toUpdate = get(id);

        if (employee.getName() != null) {
            toUpdate.setName(employee.getName());
        }

        if (employee.getType() != null) {
            toUpdate.setType(employee.getType());
        }

        toUpdate = employeeRepository.save(toUpdate);

        return toUpdate;
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

    public List<Employee> getAllFrees() {
        List<Employee> employees = employeeRepository.findAllFrees();
        return employees;
    }
}
