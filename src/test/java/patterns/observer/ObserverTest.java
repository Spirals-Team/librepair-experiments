
package patterns.observer;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * Observer Test class.
 */
public class ObserverTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(ObserverTest.class);

    /**
     * Unit Test to update.
     */
    @Test
    public void testUpdate() {
        LOG.info("{}.testUpdate", this.getClass().getSimpleName());
        final Subject subject = new Subject();
        assertNotNull(subject);
        final Observer observer = new Observer();
        assertNotNull(observer);

        subject.attachObserver(observer);
        subject.setStatus(true);
        subject.detachObserver(observer);
    }
}
