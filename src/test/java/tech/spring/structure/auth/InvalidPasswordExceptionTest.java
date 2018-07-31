package tech.spring.structure.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import tech.spring.structure.auth.exception.InvalidPasswordException;

@RunWith(SpringRunner.class)
public class InvalidPasswordExceptionTest {

    @Test(expected = InvalidPasswordException.class)
    public void testThrowInvalidPasswordException() {
        throw new InvalidPasswordException("This is a test!");
    }

}
