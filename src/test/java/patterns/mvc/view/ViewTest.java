
package patterns.mvc.view;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * ViewTest Class.
 */
public class ViewTest {

    /**
     * view.
     */
    @Test
    public void testShowView() {
        final View view = new View();
        assertNotNull("Value cannot be null", view);
        view.show();
    }

}
