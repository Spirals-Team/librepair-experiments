package com.roldukhine.entity;

import com.roldukhine.api.EmployeeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    private final PhoneService phoneService;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao, PhoneService phoneService) {
        this.employeeDao = employeeDao;
        this.phoneService = phoneService;
    }

    @Transactional
    public void addEmployee(Employee employee) {
        logger.debug("addEmployee {}", employee);
        employeeDao.insert(employee);

        List<Phone> phoneList = employee.getPhoneList();
        logger.debug("phoneList {}", phoneList);
        phoneList.forEach(phone -> phoneService.addPhone(phone, employee));
    }

    public Employee getEmployee(long id) {
        logger.debug("getEmployee {}", id);
        return employeeDao.get(id);
    }

    @Transactional
    public void updateEmployee(Employee employee) {
        logger.debug("updateEmployee {}", employee);
        employeeDao.update(employee);
    }

    @Transactional
    public void delete(long id) {
        logger.debug("delete by id {}", id);
        employeeDao.delete(id);
    }

    public List<Employee> getAll() {
        logger.debug("getAll");
        return employeeDao.getAll();
    }

    @Transactional
    public void updatePhoto(Employee employee, byte[] photo) {
        logger.debug("updatePhoto, employee {}, photo {}", employee, photo);
        employeeDao.updatePhoto(employee, photo);
    }
}
