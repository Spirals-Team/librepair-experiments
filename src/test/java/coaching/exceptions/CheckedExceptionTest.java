
package coaching.exceptions;

import org.junit.Test;

/**
 * Checked Exception Test class.
 */
public class CheckedExceptionTest {

    /**
     * Unit Test to checked exception.
     *
     * @throws CheckedException
     *             the checked exception
     */
    @Test(expected = CheckedException.class)
    public void testCheckedException() throws CheckedException {
        throw new CheckedException();
    }
}
