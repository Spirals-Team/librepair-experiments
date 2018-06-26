
package coaching.fileio;

import coaching.resources.CurrentWorkingDirectory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * class CurrentWorkingDirectoryTest.
 */
public class CurrentWorkingDirectoryTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(CurrentWorkingDirectoryTest.class);

    /**
     * Unit Test to current working directory.
     */
    @Test
    public void testCurrentWorkingDirectory() {
        LOG.info("testCurrentWorkingDirectory");
        final CurrentWorkingDirectory cwd = new CurrentWorkingDirectory();
        assertNotNull("Value cannot be null", cwd);
    }

}
