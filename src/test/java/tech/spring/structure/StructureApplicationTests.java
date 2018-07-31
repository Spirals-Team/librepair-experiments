package tech.spring.structure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static tech.spring.structure.StructureConstants.PASSWORD_DURATION_IN_DAYS;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StructureApplicationTests {

    @Value("${structure.password.duration:180}")
    private int structurePasswordDuration;

    @Test
    public void testContextLoads() {
        assertTrue("Structure application context failed to load!", true);
    }

    @Test
    public void testPasswordDuration() {
        assertEquals("Property constant password duration was not set!", structurePasswordDuration, PASSWORD_DURATION_IN_DAYS);
    }

    @Test
    public void testStructureContants() {
        StructureConstants structureConstants = new StructureConstants();
        assertNotNull("Unable to instantiate structure constants!", structureConstants);
    }

    @Test
    public void testStructureApplicationConfigure() {
        StructureApplication application = new StructureApplication();
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        application.configure(builder);
    }

    @Test
    public void testStructureApplicationMain() {
        StructureApplication.main(new String[0]);
    }

}
