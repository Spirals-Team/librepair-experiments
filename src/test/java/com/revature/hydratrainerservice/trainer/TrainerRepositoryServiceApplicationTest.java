package com.revature.hydratrainerservice.trainer;

import com.revature.hydra.trainer.HydraTrainerServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = HydraTrainerServiceApplication.class)
public class TrainerRepositoryServiceApplicationTest {

    @Test
    public void contextLoads() {

    }
}