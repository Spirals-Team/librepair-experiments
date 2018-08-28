package com.roldukhine.jdbc;

import com.roldukhine.api.DepartmentDao;
import com.roldukhine.api.EmployeeDao;
import com.roldukhine.configuration.DaoConfiguration;
import com.roldukhine.configuration.DaoConfigurationTest;
import com.roldukhine.configuration.JdbcConfiguration;
import com.roldukhine.entity.BaseEntity;
import com.roldukhine.entity.Department;
import com.roldukhine.entity.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {DaoConfiguration.class, DaoConfigurationTest.class, JdbcConfiguration.class})
@ActiveProfiles("jdbc")
public class DepartmentDaoJdbcImplTest {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    void testInsert() {
        Department department = createDepartment();
        departmentDao.insert(department);
    }

    @Test
    void testDelete() {
        Department department = createDepartment();
        departmentDao.insert(department);
        long id = department.getId();
        departmentDao.delete(department);
        BaseEntity baseEntity = departmentDao.get(id);
        Assertions.assertNull(baseEntity);
    }

    private Department createDepartment() {
        Employee employee = getManager();
        Department department = new Department();
        department.setName("IT");
        department.setManager(employee);
        return department;
    }

    private Employee getManager() {
        Employee employee = new Employee();
        employee.setFirstName("Petrov");
        employee.setSecondName("Petrov");
        employee.setLastName("Petrov");

        employee.setDepartment(null);
        employeeDao.insert(employee);
        return employee;
    }
}
