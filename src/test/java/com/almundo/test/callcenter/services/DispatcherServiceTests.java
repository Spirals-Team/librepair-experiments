package com.almundo.test.callcenter.services;

import com.almundo.test.callcenter.entities.Call;
import com.almundo.test.callcenter.entities.Employee;
import com.almundo.test.callcenter.repositories.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.jdbc.EmbeddedDatabaseConnection.H2;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE, connection = H2)
public class DispatcherServiceTests {

    @Autowired
    private DispatcherService service;

    @Value("${call.max-duration}")
    private long maxDuration;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        int numberOfEmployees = 10;
        for (int i = 1; i <= numberOfEmployees; i++) {
            Employee employee = new Employee("Operator " + i, Employee.Type.OPERATOR);
            employeeRepository.save(employee);
        }
    }


    @Test
    public void should_dispatch_one_new_call() throws InterruptedException {
        Call call = service.dispatchNewCall();

        TimeUnit.SECONDS.sleep(maxDuration + 1);

        validateFinishedCall(call);
    }

    @Test
    public void should_dispatch_ten_calls_in_parallel() throws InterruptedException {
        int numberOfCalls = 10;
        List<Call> calls = service.dispatchNewCalls(numberOfCalls);

        TimeUnit.SECONDS.sleep(maxDuration + 1);

        assertThat(calls).hasSize(numberOfCalls);
        for (Call call : calls) {
            validateFinishedCall(call);
        }
    }

    @Test(expected = AssertionError.class)
    public void should_take_more_than_max_duration_to_dispatch_more_than_ten_calls_in_parallel() throws InterruptedException {

        int numberOfCalls = 11;
        List<Call> calls = service.dispatchNewCalls(numberOfCalls);

        TimeUnit.SECONDS.sleep(maxDuration);

        assertThat(calls).hasSize(numberOfCalls);
        for (Call call : calls) {
            validateFinishedCall(call);
        }
    }

    private void validateFinishedCall(Call call) {
        assertThat(call.getFinishedDate()).isNotNull();
        assertThat(call.getAnsweredDate()).isNotNull();
        assertThat(call.getEmployee()).isNotNull();
    }
}
