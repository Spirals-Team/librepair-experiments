package com.uniovi;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import inciDashboard_e5a.InciDashboardApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=InciDashboardApplication.class)
public class InciDashboardApplicationTests {

    @Test
    public void contextLoads() {
	assertTrue(true);
    }

}
