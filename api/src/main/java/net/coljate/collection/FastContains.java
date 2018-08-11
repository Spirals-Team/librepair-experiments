package net.coljate.collection;

import net.coljate.Container;
import net.coljate.util.complexity.Complexity;
import net.coljate.util.complexity.TimeComplexity;

/**
 *
 * @author Ollie
 */
public interface FastContains extends Container {

    @Override
    @TimeComplexity(value = Complexity.CONSTANT, worstCase = Complexity.LINEAR)
    boolean contains(Object object);

}
