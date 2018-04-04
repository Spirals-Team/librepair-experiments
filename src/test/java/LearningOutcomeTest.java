import app.Category;
import app.LearningOutcome;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class LearningOutcomeTest {

    LearningOutcome lo1;
    LearningOutcome lo2;
    Category c1;


    @Before
    public void setUp() throws Exception {
        c1 = new Category("NameTest");
        lo1 = new LearningOutcome("NameTest", "DescTest");
        lo2 = new LearningOutcome("DescTest");
    }
    @Test
    public void testEmptyNameIfNotProvided() throws Exception {
        assertEquals("", lo2.getName());
        lo2.setName("NameTest2");
    }
    @Test
    public void testSetName() throws Exception {
        lo2.setName("test");
        assertEquals("test", lo2.getName());
    }

    @Test
    public void testSetCategory() throws Exception {
        lo2.setCategory(c1);
        assertEquals("NameTest", lo2.getCategory().getName());
    }

}