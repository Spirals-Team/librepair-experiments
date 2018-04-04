package com.almundo.test.callcenter.repositories;

import com.almundo.test.callcenter.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT e FROM Employee e WHERE e.currentCall IS NULL ORDER BY type ASC")
    List<Employee> findAllFrees();

    @Query(value = "SELECT e FROM Employee e WHERE e.currentCall IS NULL AND e.type = ?1")
    List<Employee> findAllFreesByType(Employee.Type type);

}
