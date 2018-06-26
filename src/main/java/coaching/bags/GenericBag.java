
package coaching.bags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Generic Type Bag Class.
 *
 * @param <T>
 *            the generic type
 */
@SuppressWarnings("serial")
public class GenericBag<T> extends ArrayList<T> implements GenericBagInterface<T> {

    /** The random. */
    private final Random random = new Random();

    /** The initial state. */
    private T[] initialState;

    /**
     * Instantiates a new empty bag.
     */
    public GenericBag() {
        super();
    }

    /**
     * Instantiates a new bag from string array. Accepts 0..N arguments.
     *
     * @param values
     *            the values
     */
    @SafeVarargs
    public GenericBag(final T... values) {
        super();
        fill(values);
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.bags.GenericBagInterface#fill(java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public GenericBagInterface<T> fill(final T... values) {
        if (values != null) {
            this.initialState = values;
            this.addAll(Arrays.asList(values));
        }
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.bags.GenericBagInterface#pick()
     */
    @Override
    public T pick() {
        return choose();
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.bags.GenericBagInterface#choose()
     */
    @Override
    public T choose() {
        final int size = size();
        if (size > 0) {
            final int nextInt = this.random.nextInt(size);
            return remove(nextInt);
        } else {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see coaching.bags.GenericBagInterface#reset()
     */
    @Override
    public GenericBagInterface<T> reset() {
        return fill(this.initialState);
    }

}
