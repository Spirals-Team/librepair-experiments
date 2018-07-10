package br.com.syntax.controledeponto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test") // Para utilizar o application-test.properties
public class ControleDePontoApplicationTests {

    @Test
    public void contextLoads() {
    }

}
