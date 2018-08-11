package net.coljate.collection.primitive;

import java.util.function.LongPredicate;

/**
 * @author ollie
 */
public enum Integers implements LongContainer {

    ALL(l -> true),
    POSITIVE(l -> l > 0),
    NON_ZERO(l -> l != 0),
    NEGATIVE(l -> l < 0),
    EVEN(l -> l % 2 == 0),
    ODD(l -> l % 2 != 0),
    NONE(l -> false) {
        @Override
        public boolean isEmpty() {
            return true;
        }
    };

    private final LongPredicate p;

    Integers(final LongPredicate p) {
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
