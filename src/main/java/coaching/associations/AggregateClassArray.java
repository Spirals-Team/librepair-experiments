
package coaching.associations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Using a basic Java array to implement an example UML Aggregation. The
 * Lifetime of parts is unconstrained by lifetime of the parent.
 **/
public final class AggregateClassArray {

    /** The Constant SIZE. */
    private static final int SIZE = 4;

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(AggregateClassArray.class);

    /** The aggregate. */
    // Implement an Aggregate using a Array of classes.
    private final AbstractAssociatedClass[] aggregate = new AbstractAssociatedClass[SIZE];

    /**
     * Sets the aggregate.
     *
     * @param index
     *            the index
     * @param element
     *            the element
     */
    public void setAggregate(final int index, final AbstractAssociatedClass element) {
        LOG.info("{}.execute", this.getClass().getName());
        this.aggregate[index] = element;
    }

    /**
     * Gets the aggregate.
     *
     * @param index
     *            the index
     * @return the aggregate
     */
    public AbstractAssociatedClass getAggregate(final int index) {
        LOG.info("{}.execute", this.getClass().getName());
        return this.aggregate[index];
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("AggregateClassArray [aggregate=%s]", Arrays.toString(this.aggregate));
    }

}
