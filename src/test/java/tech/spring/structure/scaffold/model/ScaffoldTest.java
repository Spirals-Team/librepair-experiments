package tech.spring.structure.scaffold.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ScaffoldTest {

    @Test
    public void testNewScaffoldOfName() {
        Scaffold scaffold = Scaffold.of("scaffold");
        assertNotNull("Unable to create scaffold!", scaffold);
        assertEquals("Scaffold did not have correct name!", "scaffold", scaffold.getName());
    }

    @Test
    public void testSetName() {
        Scaffold scaffold = Scaffold.of("scaffold");
        scaffold.setName("name");
        assertEquals("Scaffold did not set name!", "name", scaffold.getName());
    }

    @Test
    public void testSetAuthorization() {
        Scaffold scaffold = Scaffold.of("scaffold");
        scaffold.setAuthorization("authorization");
        assertEquals("Scaffold did not set authorization!", "authorization", scaffold.getAuthorization());
    }

    @Test
    public void testSetProperties() {
        Scaffold scaffold = Scaffold.of("scaffold");
        List<Property> properties = new ArrayList<Property>();
        scaffold.setProperties(properties);
        assertNotNull("Scaffold did not set properties!", scaffold.getProperties());
        assertEquals("Scaffold properties set had incorred size!", properties.size(), scaffold.getProperties().size());
    }

}
