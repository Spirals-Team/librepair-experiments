
package patterns.composite;

import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import static org.junit.Assume.assumeNotNull;

/**
 * Composite class tests.
 */
public class CompositeTest {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(CompositeTest.class);

    /**
     * Unit Test a typical usage of composite.
     */
    @Test
    public void testCompositeTypicalUsage() {
        // Given a composite
        final AbstractComponent composite = new Composite();
        assertNotNull("composite cannot be null", composite);

        // And a Leaf
        final Leaf leaf = new Leaf();
        assertNotNull("leaf cannot be null", leaf);

        // When we add the leaf to the composite
        assertNotNull("composite cannot be null", composite.add(leaf));

        // Then
        final ComponentInterface operation = composite.operation();
        assertNotNull("composite cannot be null", operation);
        LOG.debug("composite = {}", composite.toString());
    }

    @Test
    public void type() {
        assertThat(Composite.class, notNullValue());
    }

    @Test
    public void testAdd() {
        // Given a composite
        final Composite target = new Composite();
        assumeNotNull(target);
        // With a leaf
        final AbstractComponent component = new Leaf();
        assumeNotNull(component);

        // When
        final ComponentInterface actual = target.add(component);

        // Then
        assertNotNull(actual);
    }

    @Test
    public void testRemove() {
        // Given a composite
        final Composite target = new Composite();
        assumeNotNull(target);
        // With a leaf
        final AbstractComponent component = new Leaf();
        assumeNotNull(component);
        target.add(component);

        // When
        final ComponentInterface actual = target.remove(component);

        // Then
        assertNotNull(actual);
    }

    @Test
    public void testGetChild() {
        // Given a composite
        final Composite target = new Composite();
        assumeNotNull(target);

        // With a leaf
        final AbstractComponent component = new Leaf();
        assumeNotNull(component);
        target.add(component);
        final int index = 0;

        // When we get the leaf
        final ComponentInterface actual = target.getChild(index);

        // Then
        assertNotNull(actual);
    }

    @Test
    public void testOperation() {
        // Given a composite
        final Composite target = new Composite();
        assumeNotNull(target);

        // When
        final ComponentInterface actual = target.operation();

        // Then
        assertNotNull(actual);
    }

}
