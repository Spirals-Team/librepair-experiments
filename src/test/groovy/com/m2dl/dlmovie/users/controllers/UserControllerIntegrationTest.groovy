package com.m2dl.dlmovie.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;


}
