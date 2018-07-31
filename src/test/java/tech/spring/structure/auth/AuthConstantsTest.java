package tech.spring.structure.auth;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AuthConstantsTest {

    @Test
    public void testNewAuthConstants() {
        AuthConstants authConstants = new AuthConstants();
        assertNotNull("Unable to create new auth constants!", authConstants);
    }

}
