package com.roldukhine.jdbc;

import com.roldukhine.api.EmployeeDao;
import com.roldukhine.configuration.DaoConfiguration;
import com.roldukhine.configuration.DaoConfigurationTest;
import com.roldukhine.configuration.JdbcConfiguration;
import com.roldukhine.entity.Employee;
import com.roldukhine.entity.Phone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {DaoConfiguration.class, DaoConfigurationTest.class, JdbcConfiguration.class})
@ActiveProfiles("jdbc")
public class EmployeeDaoJdbcImplTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    void testInsert() {
        Employee employee = createTestEmployee();
        employeeDao.insert(employee);
        Assertions.assertNotEquals(0, employee.getId());
    }

    @Test
    void testGetAllWithHibernate() {
        employeeDao.getAll();
    }

    @Test
    void testSelect() {
        Employee employee = createTestEmployee();
        employee.setPhoneList(new ArrayList<Phone>());
        employeeDao.insert(employee);
        long id = employee.getId();
        employeeDao.get(id);
    }

    @Test
    void testDelete() {
        Employee employee = new Employee();
        employee.setFirstName("Petrov");
        employee.setSecondName("Petrov");
        employee.setLastName("Petrov");
        employeeDao.insert(employee);
        employeeDao.delete(employee);
    }

    @Test
    void testUpdate() {
        Employee employee = new Employee();
        employee.setFirstName("Petrov");
        employee.setSecondName("Petrov");
        employee.setLastName("Petrov");
        employee.setDepartment(null);
        employeeDao.insert(employee);
        Assertions.assertEquals("Petrov", employee.getLastName());
        employeeDao.update(employee);
        employeeDao.delete(employee);
    }

    @Test
    void testUpdatePhoto() {
        Employee employee = new Employee();
        employee.setFirstName("Petrov");
        employee.setSecondName("Petrov");
        employee.setLastName("Petrov");
        employeeDao.insert(employee);
        byte[] photo = new byte[10];
        employeeDao.updatePhoto(employee, photo);
        Employee employeeAfterUpdate = employeeDao.get(employee.getId());
        Assertions.assertEquals(employeeAfterUpdate, employee);
    }

    private Employee createTestEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Ivan");
        employee.setSecondName("Petrovich");
        employee.setLastName("Petrov");
        employee.setEmail("ivan@petrov.ru");
        employee.setWorkAddress("SPB");
        employee.setHomeAddress("Moscow");
        employee.setSkype("petrovSkype");
        employee.setNote("note");
        return employee;
    }
}
