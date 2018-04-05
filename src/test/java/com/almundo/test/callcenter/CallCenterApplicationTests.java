package com.almundo.test.callcenter;

import com.almundo.test.callcenter.controllers.CallController;
import com.almundo.test.callcenter.controllers.DispatcherController;
import com.almundo.test.callcenter.controllers.EmployeeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CallCenterApplicationTests {

    @Autowired
    private DispatcherController dispatcherController;

    @Autowired
    private CallController callController;

    @Autowired
    private EmployeeController employeeController;

    @Test
    public void contexLoads() {
        assertThat(dispatcherController).isNotNull();
        assertThat(callController).isNotNull();
        assertThat(employeeController).isNotNull();
    }

}
