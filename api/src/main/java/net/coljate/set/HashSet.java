package net.coljate.set;

import net.coljate.collection.FastContains;

/**
 *
 * @author Ollie
 * @since 1.0
 */
public interface HashSet<T> extends Set<T>, FastContains {

    @Override
    boolean contains(Object object);

}
