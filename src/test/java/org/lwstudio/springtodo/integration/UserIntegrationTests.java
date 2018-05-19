package org.lwstudio.springtodo.integration;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lwstudio.springtodo.model.entity.User;
import org.lwstudio.springtodo.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getUsers() {
        restTemplate.postForEntity("/api/users", new UserDTO("first", "password"), User.class);
        restTemplate.postForEntity("/api/users", new UserDTO("second", "password"), User.class);

        ResponseEntity<User[]> responseEntity =
            restTemplate.getForEntity("/api/users", User[].class);

        User[] users = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(users.length > 0);
    }

    @Test
    public void createUser() {
        ResponseEntity<User> responseEntity =
            restTemplate.postForEntity("/api/users", new UserDTO("Foo", "Bar"), User.class);

        User user = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Foo", user.getUsername());
    }

    @Test
    public void getUserById() {
        restTemplate.postForEntity("/api/users", new UserDTO("Wow", "New"), User.class);

        ResponseEntity<User> getResponseEntity =
            restTemplate.getForEntity("/api/users/1", User.class);

        User user = getResponseEntity.getBody();

        assertEquals(HttpStatus.OK, getResponseEntity.getStatusCode());
        assertEquals(true, user.getUsername().length() > 0);
    }

}