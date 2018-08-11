package net.coljate.list.impl;

import net.coljate.list.ImmutableArray;

/**
 *
 * @author Ollie
 */
public class ImmutableJoinArray<T>
        extends ImmutableJoinList<T>
        implements ImmutableArray<T> {

    public static <T> ImmutableArray<T> of(
            final ImmutableArray<? extends T> left,
            final ImmutableArray<? extends T> right) {
        if (left.isEmpty()) {
            return (ImmutableArray<T>) right;
        } else if (right.isEmpty()) {
            return (ImmutableArray<T>) left;
        } else {
            return new ImmutableJoinArray<>(left, right);
        }
    }

    private final ImmutableArray<? extends T> left, right;

    protected ImmutableJoinArray(
            final ImmutableArray<? extends T> left,
            final ImmutableArray<? extends T> right) {
        super(left, right);
        this.left = left;
        this.right = right;
    }

    @Override
    public ImmutableArray<T> reversedCopy() {
        return new ImmutableJoinArray<>(right.reversedCopy(), left.reversedCopy());
    }

    @Override
    public int length() {
        return left.length() + right.length();
    }

    @Override
    public T get(final int index) {
        return index < left.length()
                ? left.get(index)
                : right.get(index - left.length());
    }

}
