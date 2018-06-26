
package coaching.solid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public class MultipleResponsibility {
        private boolean foo;
        private boolean bar;

        /**
         * Foo and Bar responsibility.
         */
        public void responsibility() {
            LOG.info("responsibility");
            foo = true;
            bar = true;
        }
    }

    public class Foo {
        private boolean foo;
        public void foo() {
            LOG.info("foo");
            foo = true;
        }
    }

    public class Bar {
        private boolean bar;
        public void bar() {
            LOG.info("bar");
            bar = true;
        }
    }

    public class SingleResponsibility {
        private Foo foo = new Foo();
        private Bar bar = new Bar();
        public void responsibility() {
            LOG.info("responsibility");
            foo.foo();
            bar.bar();
        }
    }

    public void multipleResponsibility() {
        // TODO Auto-generated method stub
        
    }

    public void singleResponsibility() {
        // TODO Auto-generated method stub
        
    }
}
