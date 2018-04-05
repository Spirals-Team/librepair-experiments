package com.almundo.test.callcenter.repositories;

import com.almundo.test.callcenter.entities.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.jdbc.EmbeddedDatabaseConnection.H2;


@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE, connection = H2)
public class EmployeeRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository repository;

    @Test
    public void should_store_a_employee() {
        // given
        Employee employee = new Employee("Operator 1", Employee.Type.OPERATOR);
        entityManager.persist(employee);

        // when
        Employee found = repository.getOne(employee.getId());

        // then
        assertThat(found).isEqualTo(employee);
        assertThat(found.getId()).isOfAnyClassIn(Long.class);
        assertThat(found.getId()).isNotNull();
    }

    @Test
    public void should_find_all_employees() {
        Employee employee1 = new Employee("Operator 1", Employee.Type.OPERATOR);
        entityManager.persist(employee1);

        Employee employee2 = new Employee("Operator 2", Employee.Type.OPERATOR);
        entityManager.persist(employee2);

        Employee employee3 = new Employee("Operator 3", Employee.Type.OPERATOR);
        entityManager.persist(employee3);

        List<Employee> employees = repository.findAll();

        assertThat(employees).hasSize(3).contains(employee1, employee2, employee3);
    }

    @Test
    public void should_find_all_in_progress_employees_if_all_are_not_busy() {
        Employee employee = new Employee("Operator 1", Employee.Type.OPERATOR);
        entityManager.persist(employee);

        List<Employee> employees = repository.findAllAvailables();

        assertThat(employees).hasSize(1).contains(employee);
    }

    @Test
    public void should_find_no_free_employees_if_all_are_busy() {
        Employee employee = new Employee("Operator 1", Employee.Type.OPERATOR);
        employee.setBusy(true);
        entityManager.persist(employee);

        List<Employee> employees = repository.findAllFrees();

        assertThat(employees).hasSize(0);
    }

    @Test
    public void should_find_all_free_operator_employees_if_all_are_not_busy() {
        Employee employee = new Employee("Operator 1", Employee.Type.OPERATOR);
        entityManager.persist(employee);

        Employee manager = new Employee("Manager 1", Employee.Type.MANAGER);
        entityManager.persist(manager);

        List<Employee> employees = repository.findAllFreesByType(Employee.Type.OPERATOR);

        assertThat(employees).hasSize(1).contains(employee);
    }

    @Test
    public void should_find_no_free_operator_employees_if_all_are_busy() {
        Employee employee = new Employee("Operator 1", Employee.Type.OPERATOR);
        employee.setBusy(true);
        entityManager.persist(employee);

        Employee manager = new Employee("Manager 1", Employee.Type.MANAGER);
        entityManager.persist(manager);

        List<Employee> employees = repository.findAllFreesByType(Employee.Type.OPERATOR);

        assertThat(employees).hasSize(0);
    }

}
