package tech.spring.structure.auth.model.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class LoginRequestTest {

    @Test
    public void testNewLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        assertNotNull("Could not create login request", loginRequest);
    }

    @Test
    public void testSetUsername() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        assertEquals("Username not set as expected!", "username", loginRequest.getUsername());
    }

    @Test
    public void testSetPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("password");
        assertEquals("Password not set as expected!", "password", loginRequest.getPassword());
    }

}
