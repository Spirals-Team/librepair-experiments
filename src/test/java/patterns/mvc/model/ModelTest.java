
package patterns.mvc.model;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * The ModelTest Class.
 */
public class ModelTest {

    /**
     * Unit Test to.
     */
    @Test
    public void test() {
        final Model model = new Model();
        assertNotNull("Value cannot be null", model);
    }
}
