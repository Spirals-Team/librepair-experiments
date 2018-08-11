package net.coljate.list.impl;

import java.util.Objects;
import java.util.function.Supplier;

import net.coljate.list.ImmutableList;
import net.coljate.list.ImmutableListIterator;

/**
 *
 * @author Ollie
 */
public class ImmutableJoinList<T> implements ImmutableList<T> {

    @SuppressWarnings("unchecked")
    public static <T> ImmutableList<T> of(
            final ImmutableList<? extends T> left,
            final ImmutableList<? extends T> right) {
        if (right.isEmpty()) {
            return (ImmutableList<T>) left;
        } else if (left.isEmpty()) {
            return (ImmutableList<T>) right;
        } else {
            return new ImmutableJoinList<>(left, right);
        }
    }

    private final ImmutableList<? extends T> left, right;

    protected ImmutableJoinList(
            final ImmutableList<? extends T> left,
            final ImmutableList<? extends T> right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
    }

    @Override
    public ImmutableListIterator<T> iterator() {
        return new JoinListIterator(left::iterator, right::iterator);
    }

    @Override
    public ImmutableListIterator<T> reverseIterator() {
        return new JoinListIterator(right::reverseIterator, left::reverseIterator);
    }

    @Override
    public T first() {
        return left.first();
    }

    @Override
    public T last() {
        return right.last();
    }

    @Override
    public ImmutableList<T> reversedCopy() {
        return new ImmutableJoinList<>(right.reversedCopy(), left.reversedCopy());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + ": [" + left
                + " union " + right
                + ']';
    }

    private final class JoinListIterator implements ImmutableListIterator<T> {

        private final Supplier<ImmutableListIterator<? extends T>> first, second;
        private ImmutableListIterator<? extends T> l, r;
        private ImmutableListIterator<? extends T> current;

        private JoinListIterator(Supplier<ImmutableListIterator<? extends T>> first, Supplier<ImmutableListIterator<? extends T>> second) {
            this.first = first;
            this.second = second;
        }

        ImmutableListIterator<? extends T> current() {
            if (current == null) {
                l = first.get();
                current = l;
            }
            if (current == l && !current.hasNext()) {
                r = second.get();
                current = r;
            }
            return current;
        }

        @Override
        public boolean hasPrevious() {
            return this.current().hasPrevious();
        }

        @Override
        public T previous() {
            return this.current().previous();
        }

        @Override
        public boolean hasNext() {
            return this.current().hasNext();
        }

        @Override
        public T next() {
            return this.current().next();
        }

    }

}
