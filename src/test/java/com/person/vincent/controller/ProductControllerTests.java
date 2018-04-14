package com.person.vincent.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;

/**
 * Author: vincent
 * Date: 2018-04-07 00:45:00
 * Comment:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Resource
    private MockMvc mockMvc;

    @Test
    public void index() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products?theme_id=1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
