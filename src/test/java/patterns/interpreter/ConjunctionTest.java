
package patterns.interpreter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * class ConjunctionTest.
 */
public class ConjunctionTest {

    /**
     * class Mock.
     */
    public final class Mock extends AbstractExpression {

        /**
         * Instantiates a new mock.
         *
         * @param result
         *            the result
         */
        public Mock(final boolean result) {
            super(result);
        }
    }

    /**
     * class Pass.
     */
    public final class Pass extends AbstractExpression {

        /**
         * Instantiates a new pass.
         */
        public Pass() {
            super(true);
        }
    }

    /**
     * class Fail.
     */
    public final class Fail extends AbstractExpression {

        /**
         * Instantiates a new fail.
         */
        public Fail() {
            super(false);
        }
    }

    /**
     * Unit Test to conjunction.
     */
    @Test
    public void testConjunction() {
        final Conjunction conjunction = new Conjunction(new Mock(true), new Mock(true));
        assertNotNull("Value cannot be null", conjunction);
    }

    /**
     * Unit Test to interpret pass.
     */
    @Test
    public void testInterpretPass() {
        final Conjunction conjunction = new Conjunction(new Pass(), new Pass());
        assertNotNull("Value cannot be null", conjunction);
        final boolean interpret = conjunction.interpret(new Context());
        assertTrue(interpret);
    }

    /**
     * Unit Test to interpret fail.
     */
    @Test
    public void testInterpretFail() {
        final Context context = new Context();
        assertNotNull("Value cannot be null", context);
        assertFalse(new Conjunction(new Pass(), new Fail()).interpret(context));
        assertFalse(new Conjunction(new Fail(), new Pass()).interpret(context));
        assertFalse(new Conjunction(new Fail(), new Fail()).interpret(context));
    }

}
