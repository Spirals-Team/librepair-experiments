package com.rexam.securityTest;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.rexam.security.service.CustomizeAuthenticationSuccessHandler;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
//@WebAppConfiguration
////@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class CustomLoginRequestBuilderAuthenticationTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc;
    


    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    public void authenticationSuccess() throws Exception {
        mvc
            .perform(login())
//            .andExpect(status().isMovedTemporarily())
//            .andExpect(redirectedUrl("/rexam/showTeachingUnits"))
            .andExpect(authenticated().withUsername("srowlands0@vimeo.com").withRoles("student"));
    }

//    @Test
//    public void authenticationFailed() throws Exception {
//        mvc
//            .perform(login().user("notfound").password("invalid"))
//            .andExpect(status().isMovedTemporarily())
//            .andExpect(redirectedUrl("/authenticate?error"))
//            .andExpect(unauthenticated());
//    }

    static FormLoginRequestBuilder login() {
        return SecurityMockMvcRequestBuilders
                .formLogin("/login")
                    .userParameter("srowlands0@vimeo.com")
                    .passwordParam("toto");
    }

//    @Configuration
//    @EnableWebMvcSecurity
//    @EnableWebMvc
//    static class Config extends WebSecurityConfigurerAdapter {
//    	
//        @Autowired
//        private CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//            .authorizeRequests()
//            	.antMatchers("/resources/**").permitAll()
////            	.antMatchers("*/admin/*").hasRole("admin")
//            	.antMatchers("*/rexam/*").hasRole("student")
//            	.anyRequest().authenticated()
//                .and()
//            .formLogin().successHandler(customizeAuthenticationSuccessHandler)
//                .loginPage("/login")
//                .permitAll()
//                .and()
//            .logout()
//                .permitAll();
//        }
//
//    }
}