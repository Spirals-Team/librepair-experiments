package tech.spring.structure.scaffold.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ValidationTest {

    @Test
    public void testValidationOfNameAndMessage() {
        Validation validation = Validation.of("name", "message");
        assertNotNull("Unable to create validation!", validation);
        assertEquals("Validation did not have correct name!", "name", validation.getName());
        assertEquals("Validation did not have correct message!", "message", validation.getMessage());
    }

    @Test
    public void testValidationOfNameValueAndMessage() {
        Validation validation = Validation.of("name", "value", "message");
        assertNotNull("Unable to create validation!", validation);
        assertEquals("Validation did not have correct name!", "name", validation.getName());
        assertEquals("Validation did not have correct object!", "value", validation.getValue().toString());
        assertEquals("Validation did not have correct message!", "message", validation.getMessage());
    }

    @Test
    public void testSetName() {
        Validation validation = Validation.of("name", "value", "message");
        validation.setName("foo");
        assertEquals("Validation did not set the name!", "foo", validation.getName());
    }

    @Test
    public void testSetValue() {
        Validation validation = Validation.of("name", "value", "message");
        validation.setValue(10);
        assertEquals("Validation did not set the value!", 10, Integer.parseInt(validation.getValue().toString()));
    }

    @Test
    public void testSetObject() {
        Validation validation = Validation.of("name", "value", "message");
        validation.setMessage("changed");
        assertEquals("Validation did not set the message!", "changed", validation.getMessage());
    }

}
