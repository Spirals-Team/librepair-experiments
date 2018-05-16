package io.joaopinheiro.userservice.integration;


import io.joaopinheiro.userservice.User;
import io.joaopinheiro.userservice.UserBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntegrationTests{

    @LocalServerPort
    private int port;

    @Before
    public void init(){
        BASE_URL = "http://localhost:"+port+"/users/";
    }

    private String BASE_URL;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void canCreateUser(){
        User user = new UserBuilder().build();

        //Make sure user is not present
        assertEquals(HttpStatus.NOT_FOUND, this.restTemplate.getForEntity(BASE_URL+user.getId(),User.class).getStatusCode());
        //Register the user
        assertEquals(HttpStatus.CREATED, this.restTemplate.postForEntity(BASE_URL, user, User.class).getStatusCode());
        //Confirm that User was registered
        assertEquals(HttpStatus.OK, this.restTemplate.getForEntity(BASE_URL+user.getId(),User.class).getStatusCode());

     }


    @Test
    public void canCreateMultipleUsers(){
        User userA = new UserBuilder().withID(1L).build();
        User userB = new UserBuilder().withID(2L).build();

        List<User> users = Arrays.asList(userA,userB);

        assertTrue(this.restTemplate.exchange(BASE_URL,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>(){}).getBody().isEmpty());

        assertEquals(HttpStatus.CREATED, this.restTemplate.postForEntity(BASE_URL, userA, User.class).getStatusCode());
        assertEquals(HttpStatus.CREATED, this.restTemplate.postForEntity(BASE_URL, userB, User.class).getStatusCode());

        List<User> result = this.restTemplate.exchange(BASE_URL,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>(){}).getBody();

        assertEquals(users, result);
    }

    @Test
    public void canDeleteUser(){
        User user = new UserBuilder().build();

        //Register the User
        assertEquals(HttpStatus.CREATED, this.restTemplate.postForEntity(BASE_URL, user, User.class).getStatusCode());
        //Confirm that User was registered
        assertEquals(HttpStatus.OK, this.restTemplate.getForEntity(BASE_URL+user.getId(),User.class).getStatusCode());
        //Delete the User
        this.restTemplate.delete(BASE_URL+user.getId());
        //Make sure user is not present
        assertEquals(HttpStatus.NOT_FOUND, this.restTemplate.getForEntity(BASE_URL+user.getId(),User.class).getStatusCode());
    }

    @Test
    public void canUpdateUser(){
        User user = new UserBuilder().build();
        User userUpdate = new UserBuilder().withUsername("newUser").build();

        //Register the User
        assertEquals(HttpStatus.CREATED, this.restTemplate.postForEntity(BASE_URL, user, User.class).getStatusCode());
        //Confirm that User was registered
        assertEquals(HttpStatus.OK, this.restTemplate.getForEntity(BASE_URL+user.getId(),User.class).getStatusCode());
        HttpEntity<User> userHttpEntity = new HttpEntity<>(userUpdate);
        //Update the User
        assertEquals(HttpStatus.OK, this.restTemplate.exchange(BASE_URL+userUpdate.getId(), HttpMethod.PUT,userHttpEntity , User.class).getStatusCode());
        //Confirm that User was updated
        assertEquals(userUpdate, this.restTemplate.getForEntity(BASE_URL+user.getId(),User.class).getBody());
    }

}
