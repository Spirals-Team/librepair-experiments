
package coaching.idioms;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

import static org.junit.Assert.assertNotNull;

/**
 * DateHelperTest Class.
 */
public class DateHelperTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(DateHelperTest.class);

    /**
     * Unit Test to now.
     */
    @Test
    public void testNow() {
        LOG.info("testNow");
        final Calendar now = DateHelper.now();
        assertNotNull("Value cannot be null", now);
        LOG.info("testNow = {}", now.toString());
    }

    /**
     * Unit Test to yesterday.
     */
    @Test
    public void testYesterday() {
        LOG.info("testYesterday");
        final Calendar yesterday = DateHelper.yesterday();
        assertNotNull("Value cannot be null", yesterday);
        LOG.info("testNow = {}", yesterday.toString());
        final Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, -1);
    }

    /**
     * Unit Test to tomorrow.
     */
    @Test
    public void testTomorrow() {
        LOG.info("testTomorrow");
        final Calendar tomorrow = DateHelper.tomorrow();
        assertNotNull("Value cannot be null", tomorrow);
        LOG.info("testNow = {}", tomorrow.toString());
        final Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, +1);
    }

}
