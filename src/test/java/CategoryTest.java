import app.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class CategoryTest {

    Category c1;
    Category c2;


    @Before
    public void setUp() throws Exception {
        c1 = new Category("NameTest", "DescTest");
        c2 = new Category("NameTest");
    }
    @Test
    public void testEmptyDescriptionIfNotProvided() throws Exception {
        assertEquals("", c2.getDescription());
    }
    @Test
    public void testSetName() throws Exception {
        c2.setName("test");
        assertEquals("test", c2.getName());
    }
    @Test
    public void testToString() throws Exception {
        c1.setId(1L);
        assertEquals("ID: 1, NameTest: DescTest", c1.toString());
    }

}