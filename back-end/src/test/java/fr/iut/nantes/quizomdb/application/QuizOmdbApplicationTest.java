package fr.iut.nantes.quizomdb.application;

import fr.iut.nantes.quizomdb.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = QuizOmdbApplication.class)
@ComponentScan(basePackages = "fr.iut.nantes.quizomdb")
@AutoConfigureMockMvc
public class QuizOmdbApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        Utils.setupConfig();
    }

    @Test
    public void testHome() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("<h1>Welcome !</h1>"));

    }
}