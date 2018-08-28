package com.roldukhine.jpa;

import com.roldukhine.api.EmployeeDao;
import com.roldukhine.api.PhoneDao;
import com.roldukhine.configuration.DaoConfiguration;
import com.roldukhine.configuration.DaoConfigurationTest;
import com.roldukhine.configuration.JpaConfiguration;
import com.roldukhine.entity.Employee;
import com.roldukhine.entity.Phone;
import com.roldukhine.entity.PhoneType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {DaoConfiguration.class, DaoConfigurationTest.class, JpaConfiguration.class})
@ActiveProfiles(value = "jpa")
@Transactional
public class PhoneDaoJpaImplTest {

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void insertPhoneToEmployee() {
        Employee employee = new Employee();
        employee.setLastName("Ivanov");
        employeeDao.insert(employee);

        Phone phone = new Phone();
        phone.setType(PhoneType.HOME);
        phone.setNumber("+78124928438");
        phoneDao.insert(phone);

        phoneDao.insertPhoneToEmployee(phone, employee);
        List<Phone> phoneListByEmployee = phoneDao.getPhoneListByEmployee(employee);
        Assertions.assertEquals(1, phoneListByEmployee.size());
    }

    @Test
    public void testGetPhoneListByEmployee() {
        Employee employee = new Employee();
        employee.setLastName("Ivanov");
        employeeDao.insert(employee);

        Phone phone = new Phone();
        phone.setType(PhoneType.HOME);
        phone.setNumber("+78124928438");
        phoneDao.insert(phone);

        List<Phone> phoneListByEmployee = phoneDao.getPhoneListByEmployee(employee);
        Assertions.assertEquals(0, phoneListByEmployee.size());
    }
}
