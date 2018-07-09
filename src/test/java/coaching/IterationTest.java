
package coaching;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * Examples of a iteration (looping) programming instructions.
 */
public class IterationTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(IterationTest.class);

    /**
     * The <code>for()</code> loop statement is an example of iteration.
     *
     * Q) How many times will the log code be executed?
     */
    @Test
    public void testExampleFor() {
        final int max = 0;
        int count = 0;

        for (int i = 0; i < max; i++) {
            count++;
        }

        LOG.info("loop count = {} ", count);
        assertEquals(0, count);
    }

    /**
     * The <code>for()</code> loop statement is an example of iteration.
     *
     * Q) How many times will the log code be executed?
     */
    @Test
    public void testExampleForCountDown() {
        final int start = 10;
        final int end = 10;
        int count = 0;

        for (int i = start; i > end; i--) {
            count++;
        }

        LOG.info("loop count = {} ", count);
        assertEquals(0, count);
    }

    /**
     * The <code>while(not false)</code> statement is an example of iteration.
     *
     * Q) How many times will the log code be executed?
     */
    @Test
    public void testExampleWhileNotFalse() {
        boolean loop;
        loop = false;
        int count = 0;

        LOG.info("while({})", loop);
        while (loop) {
            count++;
            loop = false;
        }

        LOG.info("loop count = {} ", count);
        assertEquals(0, count);
    }

    /**
     * The <code>while(true)</code> statement is an example of iteration.
     *
     * Q) How many times will the log code be executed?
     */
    @Test
    public void testExampleWhileTrue() {
        boolean exit = false;
        int count = 0;

        LOG.info("while({})", exit);
        while (!exit) {
            count++;
            exit = true;
        }
        LOG.info("loop count = {} ", count);
        assertEquals(1, count);
    }

    /**
     * The <code>do { ... } while(true)</code> statement is an example of an
     * iteration.
     *
     * Q) How many times will the code be executed?
     */
    @Test
    public void testExampleDoWhileTrue() {
        boolean loop = true;
        int count = 0;

        LOG.info("do while({})", loop);
        do {
            count++;
            loop = true;
        } while (!loop);

        LOG.info("loop count = {} ", count);
        assertEquals(1, count);
    }

    /**
     * The <code>do { ... } while(true)</code> statement is an example of an
     * iteration.
     *
     * Q) How many times will the code be executed?
     */
    @Test
    public void testExampleDoWhileFalse() {
        boolean exit = false;
        int count = 0;

        LOG.info("do while({})", exit);
        do {
            count++;
            exit = true;
        } while (!exit);

        LOG.info("loop count = {} ", count);
        assertEquals(1, count);
    }

}
