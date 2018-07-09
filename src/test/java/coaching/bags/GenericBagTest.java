
package coaching.bags;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * The GenericBagTest class.
 */
public class GenericBagTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(GenericBagTest.class);

    /** The Constant VALUES. */
    private static final String[] VALUES = { "Heads", "Tails" };

    /**
     * Unit Test to generic bag.
     */
    @Test
    public void testGenericBag() {
        final GenericBag<String> genericBag = new GenericBag<>();
        assertNotNull(genericBag);
        LOG.debug("genericBag = {}", genericBag);
    }

    /**
     * Unit Test to generic bag null.
     */
    @Test
    public void testGenericBagNull() {
        final GenericBag<String> genericBag = new GenericBag<String>((String[]) null);
        assertNotNull(genericBag);
        LOG.debug("genericBag = {}", genericBag);
    }

    /**
     * Unit Test to generic bag string array.
     */
    @Test
    public void testGenericBagStringArray() {
        final GenericBag<String> genericBag = new GenericBag<>(VALUES);
        assertNotNull(genericBag);
        LOG.debug("genericBag = {}", genericBag);
    }

    /**
     * Unit Test to generic bag vargs.
     */
    @Test
    public void testGenericBagVargs() {
        final GenericBag<String> genericBag = new GenericBag<>("Heads", "Tails");
        assertNotNull(genericBag);
        LOG.debug("genericBag = {}", genericBag);
    }

    /**
     * Unit Test to generic bag T array.
     */
    @Test
    public void testGenericBagTArray() {
        final GenericBag<Boolean> genericBag = new GenericBag<>(true, false);
        assertNotNull(genericBag);
        LOG.debug("genericBag = {}", genericBag);
    }

    /**
     * Unit Test to fill vargs.
     */
    @Test
    public void testFillVargs() {
        final GenericBag<String> genericBag = new GenericBag<>();
        assertNotNull(genericBag);
        genericBag.fill("Heads", "Tails");
        LOG.debug("genericBag = {}", genericBag);
    }

    /**
     * Unit Test to fill null.
     */
    @Test
    public void testFillNull() {
        final GenericBag<String> genericBag = new GenericBag<>();
        assertNotNull(genericBag);
        genericBag.fill((String[]) null);
        LOG.debug("genericBag = {}", genericBag);
    }

    /**
     * Unit Test to fill string array.
     */
    @Test
    public void testFillStringArray() {
        final GenericBag<String> genericBag = new GenericBag<>();
        assertNotNull(genericBag);
        genericBag.fill(VALUES);
        LOG.debug("genericBag = {}", genericBag);
    }

    /**
     * Unit Test to pick.
     */
    @Test
    public void testPick() {
        final GenericBag<String> genericBag = new GenericBag<>("Heads", "Tails");
        assertNotNull(genericBag);
        LOG.debug("genericBag = {}", genericBag.pick());
    }

    /**
     * Unit Test to choose.
     */
    @Test
    public void testChoose() {
        final GenericBag<String> genericBag = new GenericBag<>("Heads", "Tails");
        assertNotNull(genericBag);
        LOG.debug("genericBag = {}", genericBag.choose());
    }

    /**
     * Unit Test to add T.
     */
    @Test
    public void testAddT() {
        final GenericBag<String> genericBag = new GenericBag<>("Heads", "Tails");
        assertNotNull(genericBag);
        genericBag.add("Edge");
        LOG.debug("genericBag = {}", genericBag.choose());
        LOG.debug("genericBag = {}", genericBag.choose());
        LOG.debug("genericBag = {}", genericBag.choose());
        LOG.debug("genericBag = {}", genericBag.choose());
    }

    /**
     * Unit Test to reset.
     */
    @Test
    public void testReset() {
        final GenericBag<String> genericBag = new GenericBag<>("Heads", "Tails");
        assertNotNull(genericBag);
        LOG.debug("genericBag = {}", genericBag);
        LOG.debug("genericBag = {}", genericBag.choose());
        LOG.debug("genericBag = {}", genericBag.choose());
        genericBag.reset();
        LOG.debug("genericBag = {}", genericBag);
        LOG.debug("genericBag = {}", genericBag.choose());
        LOG.debug("genericBag = {}", genericBag.choose());
    }

}
