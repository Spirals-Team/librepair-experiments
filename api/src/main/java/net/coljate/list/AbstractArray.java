package net.coljate.list;

/**
 *
 * @author Ollie
 */
public abstract class AbstractArray<T>
        extends AbstractList<T>
        implements Array<T> {

    @Override
    public MutableArray<T> mutableCopy() {
        return Array.super.mutableCopy();
    }

    @Override
    public ImmutableArray<T> immutableCopy() {
        return Array.super.immutableCopy();
    }

}
