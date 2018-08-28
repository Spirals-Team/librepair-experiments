package com.roldukhine.jpa;

import com.roldukhine.api.EmployeeDao;
import com.roldukhine.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Repository("employeeDaoJpaImpl")
public class EmployeeDaoJpaImpl extends AbstractDaoJpaImpl<Employee> implements EmployeeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void delete(long id) {
        logger.trace("delete with id {}", id);
        Employee employee = entityManager.find(Employee.class, id);
        if (employee != null) {
            entityManager.remove(employee);
        }
    }

    @Override
    public void updatePhoto(Employee employee, byte[] photo) {
        logger.trace("updatePhoto employee {}, photo {}", employee, photo);
        employee.setPhoto(photo);
        update(employee);
    }
}
