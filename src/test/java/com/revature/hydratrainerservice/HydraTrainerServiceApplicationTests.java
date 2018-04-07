package com.revature.hydratrainerservice;

import com.revature.hydra.trainer.HydraTrainerServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = HydraTrainerServiceApplication.class)
public class HydraTrainerServiceApplicationTests {

    @Test
    public void contextLoads() {
    }

}
