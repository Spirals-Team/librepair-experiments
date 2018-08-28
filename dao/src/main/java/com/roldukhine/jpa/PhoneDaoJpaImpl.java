package com.roldukhine.jpa;

import com.roldukhine.api.EmployeeDao;
import com.roldukhine.api.PhoneDao;
import com.roldukhine.entity.Employee;
import com.roldukhine.entity.Phone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class PhoneDaoJpaImpl extends AbstractDaoJpaImpl<Phone> implements PhoneDao {

    @Autowired
    @Qualifier("employeeDaoJpaImpl")
    private EmployeeDao employeeDao;

    @Override
    public void insertPhoneToEmployee(Phone phone, Employee employee) {
        logger.trace("insertPhoneToEmployee - phone {}, employee {}", phone, employee);
        employee.addPhoneToPhoneList(phone);
        employeeDao.update(employee);
    }

    @Override
    public List<Phone> getPhoneListByEmployee(Employee employee) {
        logger.debug("getPhoneListByEmployee employee {}", employee);
        List<Phone> phoneList = employee.getPhoneList();
        logger.debug("phoneList", phoneList);
        return phoneList;
    }
}
