package net.coljate.collection.primitive;

import net.coljate.Container;
import net.coljate.util.complexity.Complexity;
import net.coljate.util.complexity.TimeComplexity;

/**
 *
 * @author Ollie
 */
public interface LongContainer extends Container {

    @TimeComplexity(bestCase = Complexity.CONSTANT, worstCase = Complexity.LINEAR)
    boolean contains(long i);

    @Override
    default boolean contains(final Object object) {
        return (object instanceof Long || object instanceof Integer)
                && this.contains(((Number) object).longValue());
    }

}
