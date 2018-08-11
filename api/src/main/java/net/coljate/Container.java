package net.coljate;

import net.coljate.collection.Collection;
import net.coljate.collection.FastContains;
import net.coljate.util.complexity.Complexity;
import net.coljate.util.complexity.MemoryComplexity;
import net.coljate.util.complexity.TimeComplexity;

/**
 * An object that can "contain" other objects.
 * <p>
 * Finite containers are normally represented by some {@link Collection}.
 * <p>
 * Infinite containers (essentially {@link java.util.function.Predicate predicates}) can exist but are not provided as they will generally be bespoke.
 *
 * @author Ollie
 * @see Collection
 * @see FastContains
 */
public interface Container {

    /**
     * @param object
     * @return true if this container contains the given object, according to its {@link Object#equals equals} method.
     */
    @TimeComplexity(bestCase = Complexity.CONSTANT, worstCase = Complexity.LINEAR)
    @MemoryComplexity(Complexity.CONSTANT)
    boolean contains(Object object);

    /**
     * @return true if there are no objects inside this container.
     */
    @TimeComplexity(Complexity.CONSTANT)
    @MemoryComplexity(Complexity.CONSTANT)
    boolean isEmpty();

}
