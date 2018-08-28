package com.roldukhine.jpa;

import com.roldukhine.api.EmployeeDao;
import com.roldukhine.configuration.DaoConfiguration;
import com.roldukhine.configuration.DaoConfigurationTest;
import com.roldukhine.configuration.JpaConfiguration;
import com.roldukhine.entity.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {DaoConfiguration.class, DaoConfigurationTest.class, JpaConfiguration.class})
@ActiveProfiles("jpa")
@Transactional
public class EmployeeDaoJpaImplTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void testInsert() {
        Employee employee = createTestEmployee();
        employeeDao.insert(employee);
        Assertions.assertNotEquals(0, employee.getId());
    }

    @Test
    public void testDelete() {
        Employee employee = createTestEmployee();
        employeeDao.insert(employee);
        long id = employee.getId();
        employeeDao.delete(id);
        Employee employeeById = employeeDao.get(id);
        Assertions.assertNull(employeeById);
    }

    @Test
    public void testUpdate() {
        Employee employee = createTestEmployee();
        employeeDao.insert(employee);
        final String newLastName = "Obamov";
        employee.setLastName(newLastName);
        employeeDao.update(employee);

        Employee employeeById = employeeDao.get(employee.getId());
        Assertions.assertEquals(newLastName, employeeById.getLastName());
    }

    @Test
    public void testUpdatePhoto() {
        Employee employee = new Employee();
        employee.setFirstName("Petrov");
        employee.setSecondName("Petrov");
        employee.setLastName("Petrov");
        employeeDao.insert(employee);
        byte[] photo = new byte[10];
        employeeDao.updatePhoto(employee, photo);

        Employee employeeById = employeeDao.get(employee.getId());
        Assertions.assertArrayEquals(photo, employeeById.getPhoto());
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
