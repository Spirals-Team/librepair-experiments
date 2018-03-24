package com.revature.project2.helpers;

import com.revature.project2.Project2Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Project2Application.class)
@SpringBootTest
public class GoogleApiTest {

    private MockMvc mockMvc;

    @Test
    public void getLocPredictions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                post("/google/prediction")
                .param("input", "235 Hillview Terrace"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
}
