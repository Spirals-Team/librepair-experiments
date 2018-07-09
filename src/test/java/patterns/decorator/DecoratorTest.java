
package patterns.decorator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * Unit Test for an abstract Decorator class.
 */
public class DecoratorTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(DecoratorTest.class);

    public class MissingOperation extends AbstractComponent {
    }

    public class ConcreteDecorator extends AbstractComponent {
        @Override
        public AbstractComponent operation() {
            final String simpleName = this.getClass().getSimpleName();
            this.log.info(simpleName.toString());
            return this;
        }
    }

    /**
     * Unit Test to operation.
     */
    @Test
    public void testDecoratedComponentOperation() {
        final DecoratedComponent component = new DecoratedComponent();
        assertNotNull(component);
        assertNotNull(component.operation());
        LOG.info(component.toString());
    }

    /**
     * Unit Test to operation.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testMissingOperationImplementation() {
        final MissingOperation missingOperation = new MissingOperation();
        assertNotNull(missingOperation);
        missingOperation.operation();
    }

    /**
     * Unit Test to before Operation.
     */
    @Test
    public void testBeforeOperation() {
        final DecoratedComponent component = new DecoratedComponent();
        assertNotNull(component);

        final AbstractComponent behaviour = new ConcreteDecorator();
        assertNotNull(behaviour);

        assertNotNull(component.attachBefore(behaviour));
        assertNotNull(component.operation());
        assertNotNull(component.detachBefore(behaviour));
        assertNotNull(component.operation());

        LOG.info(component.toString());
    }

    /**
     * Unit Test to after Operation.
     */
    @Test
    public void testAfterOperation() {
        final DecoratedComponent component = new DecoratedComponent();
        assertNotNull(component);

        final AbstractComponent behaviour = new ConcreteDecorator();
        assertNotNull(behaviour);

        component.attachAfter(behaviour);
        component.operation();
        component.detachAfter(behaviour);
        component.operation();

        LOG.info(component.toString());
    }

}
