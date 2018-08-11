package net.coljate.list.lazy;

import java.util.function.IntFunction;

import net.coljate.util.suppliers.Suppliers;

/**
 *
 * @author Ollie
 */
public class LazySuppliedArray<T>
        implements LazyArray<T> {

    private static final Object COMPUTED_NULL = new Object();

    public static <T> LazySuppliedArray<T> create(final int length, final IntFunction<? extends T> function) {
        return new LazySuppliedArray<>(function, new Object[length]);
    }

    private final IntFunction<? extends T> supplier;
    private final Object[] store;

    protected LazySuppliedArray(final IntFunction<? extends T> supplier, final Object[] array) {
        this.supplier = supplier;
        this.store = array;
    }

    @Override
    public int length() {
        return store.length;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(final int index) {
        Object got = store[index];
        if (got == null) {
            got = supplier.apply(index);
            store[index] = Suppliers.firstNonNull(got, COMPUTED_NULL);
        }
        return got == COMPUTED_NULL ? null : (T) got;
    }

}
