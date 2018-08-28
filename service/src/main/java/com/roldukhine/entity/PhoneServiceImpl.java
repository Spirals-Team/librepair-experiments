package com.roldukhine.entity;

import com.roldukhine.api.PhoneDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PhoneServiceImpl implements PhoneService {

    private final PhoneDao phoneDao;

    @Autowired
    public PhoneServiceImpl(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Transactional
    public void addPhone(Phone phone) {
        logger.debug("addPhone {}", phone);
        phoneDao.insert(phone);
    }

    @Transactional
    @Override
    public void addPhone(Phone phone, Employee employee) {
        addPhone(phone);

        logger.debug("addPhoneToEmployee, employee {}, phone {}", employee, phone);
        phoneDao.insertPhoneToEmployee(phone, employee);
    }

    public Phone getPhone(long id) {
        logger.debug("getPhone {}", id);
        return phoneDao.get(id);
    }
}
