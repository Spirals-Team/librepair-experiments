
package coaching.solid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * A class to Demonstrate the Single Responsibility Principle (SRP).
 *
 * Intent: A class should have only on reason to change, one responsibility.
 *
 * @author martin.spamer.
 * @version 0.1 - first release.
 *          Created 13-Jan-2005 - 12:05:35
 */
public class SingleResponsibilityPrinciple {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(SingleResponsibilityPrinciple.class);

    /**
     * A class with Multiple Responsibilities.
     */
    public class MultipleResponsibility {

        /** The foo. */
        private boolean foo = false;

        /** The bar. */
        private boolean bar = false;

        /**
         * Foo and Bar responsibility.
         */
        public void responsibility() {
            LOG.info("responsibility");
            this.foo = true;
            this.bar = true;
        }

        @Override
        public String toString() {
            return String.format("MultipleResponsibility [foo=%s, bar=%s]", this.foo, this.bar);
        }
    }

    /**
     * Split out the Foo responsibility into a separate class.
     */
    public class Foo {

        /** The foo. */
        private boolean foo;

        /**
         * Foo.
         */
        public void doFoo() {
            LOG.info("doFoo");
            this.foo = true;
        }
    }

    /**
     * Split out the Bar responsibility into a separate class.
     */
    public class Bar {

        /** The bar. */
        private boolean bar;

        /**
         * Bar.
         */
        public void doBar() {
            LOG.info("doBar");
            this.bar = true;
        }
    }

    /**
     * A class with a Single Responsibility, a container for Foo and Bar.
     */
    public class SingleResponsibility {

        /** The foo. */
        private final Foo foo = new Foo();

        /** The bar. */
        private final Bar bar = new Bar();

        /**
         * Responsibility.
         */
        public void responsibility() {
            LOG.info("responsibility");
            this.foo.doFoo();
            this.bar.doBar();
        }
    }

    public void multipleResponsibility() {
        final MultipleResponsibility multipleResponsibility = new MultipleResponsibility();
        assertNotNull(multipleResponsibility);
    }

    public void singleResponsibility() {
        final SingleResponsibility singleResponsibility = new SingleResponsibility();
        assertNotNull(singleResponsibility);
    }

}
