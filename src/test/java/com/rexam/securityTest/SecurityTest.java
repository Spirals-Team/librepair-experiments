package com.rexam.securityTest;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.*;;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;








@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
//@SpringApplicationConfiguration(classes = {App.class, WebSecurityConfig.class})
public class SecurityTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    private UserDetailsService userDetailsService;
//    
//    private AuthenticationManagerBuilder auth;
    @Autowired
    private FilterChainProxy filterChainProxy;

    
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
//        			.apply(springSecurity())
//                .dispatchOptions(true)
//                .addFilters(filterChainProxy)
        			.build();
    }
    

    @Test
    public void testUserAccessForAccount() throws Exception{
    	
        mockMvc.perform(get("/login")).andExpect(status().isOk())
        .andExpect(forwardedUrl("/WEB-INF/view/login.jsp"));
        mockMvc.perform(get("/index")).andExpect(status().isOk())
        .andExpect(forwardedUrl("/WEB-INF/view/index.jsp"));
        mockMvc.perform(get("/rexam/showTeachingUnits")).andExpect(status().isOk())
        .andExpect(forwardedUrl("/WEB-INF/view/teachingUnits.jsp"));
        mockMvc.perform(get("/rexam/showExams").param("code", "ENSAGAU10")).andExpect(status().isOk())
        .andExpect(forwardedUrl("/WEB-INF/view/exams.jsp"));
        mockMvc.perform(get("/rexam/addTeachingUnits")).andExpect(status().isOk())
        .andExpect(forwardedUrl("/WEB-INF/view/index.jsp"));
//        mockMvc.perform(get("/rexam/regs").).andExpect(status().isOk())
//        .andExpect(forwardedUrl("/WEB-INF/view/regslist.jsp"));
        
        
        
        
        mockMvc.perform(formLogin("/login").user("srowlands0@vimeo.com").password("toto"));
//        .andExpect(forwardedUrl("/WEB-INF/view/teachingUnits.jsp"));
        
        mockMvc.perform(post("/login").param("username","srowlands0@vimeo.com").param("password","toto"));
//		.andExpect(redirectedUrl("rexam/showTeachingUnits"));
//        mockMvc.perform(formLogin("/login").userParameter("srowlands0@vimeo.com").passwordParam("toto"))
//		.andExpect(redirectedUrl("rexam/showTeachingUnits"));
//		.andExpect(authenticated().withUsername("srowlands0@vimeo.com"));
        
//        mockMvc.perform(get("/rexam/")).andExpect(status().isFound());
//        mockMvc.perform(post("/login").param("username", "srowlands0@vimeo.com").param("password", "toto"))
//        .andExpect(forwardedUrl("/WEB-INF/view/teachingUnits.jsp"));

//        
        
//        mockMvc.perform(get("/login").param("username", "").param("password", "")) 
//        .andExpect(status().isOk())
//        .andExpect(); 
//        .andExpect(handler().handlerType(UserController.class)) //验证执行的控制器类型  
//        .andExpect(handler().methodName("create")) //验证执行的控制器方法名  

//        .andExpect(flash().attributeExists("success")) //验证存在flash属性  
//        .andExpect(view().name("/rexam/showTeachingUnits")); //验证视图  
//        mockMvc.perform(post("/login")).andExpect(status().isOk());
//
//        mockMvc.perform(
//                get("*/rexam/*")    
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)  
//                        .param("pcode","root")
//        ).andExpect(status().isOk())                         
//                .andReturn().getResponse().getContentAsString();
//        
//        mockMvc.perform(post("*/rexam/*")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)  
//				).andExpect(status().isOk())
//				.andExpect(jsonPath("$.data.name", is("测试"))))  
//				.andExpect(jsonPath("$.data.createTime", notNullValue()))
//;
//        
        
//        mockMvc.perform(get("*/rexam/*")).andExpect(status().isOk());
    }
    

}
