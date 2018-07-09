
package patterns.memento;

import java.util.UUID;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The MementoTest class.
 */
public class MementoTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(MementoTest.class);

    /**
     * Unit Test to memento typical usage.
     */
    @Test
    public void testTypicalMemento() {
        final String state = UUID.randomUUID().toString();
        final Memento memento = new Memento(state);
        assertNotNull(memento);
        assertEquals(state, memento.getState());
        LOG.info(memento.toString());
    }

    /**
     * Unit Test to memento.
     */
    @Test
    public void testMemento() {
        final Memento memento = new Memento();
        assertNotNull(memento);
        LOG.info(memento.toString());
    }

    /**
     * Unit Test to memento.
     */
    @Test
    public void testMementoNull() {
        final Memento memento = new Memento(null);
        assertNotNull(memento);
        LOG.info(memento.toString());
    }

}
