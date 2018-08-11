package net.coljate.collection.primitive;

import net.coljate.Container;

/**
 *
 * @author Ollie
 */
public interface DoubleContainer extends Container {

    boolean contains(double d);

    @Override
    @Deprecated
    default boolean contains(final Object object) {
        return object instanceof Double
                && this.contains(((Double) object).doubleValue());
    }

}
