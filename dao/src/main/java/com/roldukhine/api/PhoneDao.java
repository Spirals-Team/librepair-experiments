package com.roldukhine.api;

import com.roldukhine.entity.Employee;
import com.roldukhine.entity.Phone;

import java.util.List;

public interface PhoneDao extends CrudDao<Phone> {
    void insertPhoneToEmployee(Phone phone, Employee employee);

    List<Phone> getPhoneListByEmployee(Employee employee);
}
