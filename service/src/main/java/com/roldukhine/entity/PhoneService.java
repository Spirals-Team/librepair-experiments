package com.roldukhine.entity;

public interface PhoneService {
    void addPhone(Phone phone);

    void addPhone(Phone phone, Employee employee);

    Phone getPhone(long id);
}
