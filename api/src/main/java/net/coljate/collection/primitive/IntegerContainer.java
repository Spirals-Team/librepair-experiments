package net.coljate.collection.primitive;

/**
 *
 * @author Ollie
 */
public interface IntegerContainer extends LongContainer {

    boolean contains(int i);

    @Override
    default boolean contains(final Object object) {
        return object instanceof Integer
                && this.contains(((Integer) object).intValue());
    }

    @Override
    default boolean contains(final long l) {
        return (l >= Integer.MIN_VALUE && l <= Integer.MAX_VALUE)
                && this.contains((int) l);
    }

}
