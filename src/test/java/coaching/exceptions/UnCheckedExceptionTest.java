
package coaching.exceptions;

import org.junit.Test;

/**
 * UnCheckedExceptionTest class.
 */
public class UnCheckedExceptionTest {

    /**
     * Unit Test to unChecked exception.
     */
    @Test(expected = UnCheckedException.class)
    public void testUnCheckedException() {
        throw new UnCheckedException();
    }
}
