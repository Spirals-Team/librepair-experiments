package tech.spring.structure.utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class StringUtilityTest {

    @Test
    public void testStringUtilityInstance() {
        StringUtility stringUtility = new StringUtility();
        assertNotNull("Unable to instantiate string utility!", stringUtility);
    }

    @Test
    public void testFormalize() {
        String formalName = StringUtility.formalize("settings");
        assertEquals("Formalize produced incorrect formal name!", "Settings", formalName);
    }

    @Test
    public void testFormalizeMultipleWords() {
        String formalName = StringUtility.formalize("testProperty");
        assertEquals("Formalize produced incorrect formal name!", "Test Property", formalName);
    }

}
