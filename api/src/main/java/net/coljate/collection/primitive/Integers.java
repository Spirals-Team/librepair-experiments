package net.coljate.collection.primitive;

import java.util.function.LongPredicate;

/**
 *
 * @author ollie
 */
public enum Integers implements LongContainer {

    POSITIVE(l -> l > 0),
    NON_ZERO(l -> l != 0),
    EVEN(l -> l % 2 == 0);

    private final LongPredicate p;

    private Integers(LongPredicate p) {
        this.p = p;
    }
    

    @Override
    public boolean contains(long i) {
        return p.test(i);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

}
