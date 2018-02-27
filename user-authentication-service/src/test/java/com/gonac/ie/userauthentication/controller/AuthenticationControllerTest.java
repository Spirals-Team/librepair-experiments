/*
  Created by h.odunsi on 05/01/2018.
 */
package com.gonac.ie.userauthentication.controller;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.web.servlet.MockMvc;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class AuthenticationControllerTest
{


   @InjectMocks
   AuthenticationController authenticationController;

   @Rule
   public MockitoRule mockitoRule = MockitoJUnit.rule();

   MockMvc mockMvc;


   @Before
   public void setUp()
   {
      mockMvc = standaloneSetup(authenticationController).build();
   }


   @Test
   public void test1() throws Exception
   {
      mockMvc.perform(get("/hello"))
            .andExpect(status().isOk())
            .andExpect(content().string("Hello world"));
   }

}
