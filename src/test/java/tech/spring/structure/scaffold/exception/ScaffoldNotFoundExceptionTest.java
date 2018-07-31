package tech.spring.structure.scaffold.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ScaffoldNotFoundExceptionTest {

    @Test(expected = ScaffoldNotFoundException.class)
    public void testThrowScaffoldNotFoundException() {
        throw new ScaffoldNotFoundException("This is a test!");
    }

}
