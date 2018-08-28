package com.roldukhine.entity;

import com.roldukhine.api.EmployeeDao;
import com.roldukhine.api.PhoneDao;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class EmployeeServiceImplTest {

    private EmployeeDao employeeDao = mock(EmployeeDao.class);
    private PhoneDao phoneDao = mock(PhoneDao.class);

    @Test
    void testAddEmployee() {
        Employee employee = new Employee();
        employee.setLastName("Ivanov");
        employee.setEmail("ivanov@gmail.com");
        List<Phone> phoneList = new ArrayList<>();
        Phone phone = new Phone();
        phone.setNumber("+7921345");
        phone.setType(PhoneType.HOME);
        phoneList.add(phone);
        employee.setPhoneList(phoneList);

        PhoneService phoneService = new PhoneServiceImpl(phoneDao);
        EmployeeService service = new EmployeeServiceImpl(employeeDao, phoneService);
        service.addEmployee(employee);

        verify(employeeDao, times(1)).insert(employee);
        verify(phoneDao, times(1)).insertPhoneToEmployee(phone, employee);
    }
}
