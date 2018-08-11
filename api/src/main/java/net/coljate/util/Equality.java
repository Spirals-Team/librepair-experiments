package net.coljate.util;

import java.util.Iterator;
import java.util.Objects;

import net.coljate.Container;
import net.coljate.collection.FastContains;
import net.coljate.util.complexity.Complexity;
import net.coljate.util.complexity.TimeComplexity;

/**
 *
 * @author Ollie
 */
public class Equality {

    @TimeComplexity(Complexity.LINEAR)
    public static boolean orderedEquals(final Iterable<?> it1, final Iterable<?> it2) {
        if (it1 == it2) {
            return true;
        }
        final Iterator<?> i1 = it1.iterator();
        final Iterator<?> i2 = it2.iterator();
        while (i1.hasNext()) {
            final Object o1 = i1.next();
            if (!i2.hasNext() || !Objects.equals(o1, i2.next())) {
                return false;
            }
        }
        return !i2.hasNext();
    }

    @TimeComplexity(bestCase = Complexity.LINEAR, worstCase = Complexity.QUADRATIC, computed = true)
    public static <A extends Container & Iterable<?>, B extends Container & Iterable<?>> boolean unorderedEquals(final A a, final B b) {
        if (a == b) {
            return true;
        }
        final int c1 = Iterables.count(a), c2 = Iterables.count(b);
        if (c1 != c2) {
            return false;
        } else if (c1 == 0) {
            return true;
        }
        final Container container;
        final Iterable<?> iterable;
        if (a instanceof FastContains) {
            container = a;
            iterable = b;
        } else {
            container = b;
            iterable = a;
        }
        for (final Object element : iterable) {
            if (!container.contains(element)) {
                return false;
            }
        }
        return true;
    }

}
