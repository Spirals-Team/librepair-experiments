
package coaching;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * A Sequence of programming instructions, one follows another.
 */
public class SequenceTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(SequenceTest.class);

    /**
     * Example of a sequence of programming instructions.
     */
    @Test
    public void testSimpleSequenceExample() {
        final int x = 1;
        final int y = 1;
        final int z = x + y;
        LOG.info("{} = {} + {}", z, x, y);
        assertEquals(2, z);
    }

    /**
     * Test function sequence example.
     */
    @Test
    public void testFunctionSequenceExample() {
        foo();
        bar();
    }

    /**
     * Bar.
     *
     * @return true, if bar
     */
    private boolean bar() {
        LOG.info("bar()");
        return true;
    }

    /**
     * Foo.
     *
     * @return true, if foo
     */
    private boolean foo() {
        LOG.info("foo()");
        return true;
    }

}
