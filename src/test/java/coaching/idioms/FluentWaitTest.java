
package coaching.idioms;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Fluent Wait Test class.
 */
public class FluentWaitTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(FluentWaitTest.class);

    /**
     * class PassCondition.
     */
    public class PassCondition extends Condition {

        /**
         * Instantiates a new pass condition.
         */
        public PassCondition() {
            super(true);
        }
    }

    /**
     * class FailCondition.
     */
    public class FailCondition extends Condition {

        /**
         * Instantiates a new fail condition.
         */
        public FailCondition() {
            super(false);
        }
    }

    /**
     * Unit Test to fluent wait.
     */
    @Test
    public void testFluentWaitDefault() {
        LOG.info("testFluentWaitDefault = {}", System.currentTimeMillis());
        final FluentWait wait = new FluentWait();
        final Condition pass = new PassCondition();
        assertTrue(wait.until(pass));
        LOG.info("end = {}", System.currentTimeMillis());
    }

    /**
     * Unit Test to fluent wait.
     */
    @Test
    public void testFluentWaitWithOverrides() {
        LOG.info("testFluentWaitDefault = {}", System.currentTimeMillis());
        final FluentWait wait = new FluentWait();

        assertEquals(wait, wait.setTimeOut(1000));
        assertEquals(1000, wait.setTimeOut());

        assertEquals(wait, wait.setInterval(100));
        assertEquals(1000, wait.setTimeOut());

        final Condition pass = new PassCondition();
        assertTrue(wait.until(pass));
        LOG.info("end = {}", System.currentTimeMillis());
    }

    /**
     * Unit Test to wait until condition true.
     */
    @Test
    public void testWaitUntilConditionTrue() {
        LOG.info("testFluentWaitDefault = {}", System.currentTimeMillis());
        final FluentWait wait = new FluentWait();
        assertTrue(wait.until(new PassCondition()));
        LOG.info("end = {}", System.currentTimeMillis());
    }

    /**
     * Unit Test to wait until condition false.
     */
    @Test
    public void testWaitUntilConditionFalse() {
        LOG.info("testFluentWaitDefault = {}", System.currentTimeMillis());
        final FluentWait wait = new FluentWait();
        // wait.until(new Condition(false));
        assertFalse(wait.until(new FailCondition()));
        LOG.info("end = {}", System.currentTimeMillis());
    }
}
