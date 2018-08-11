package net.coljate.list.impl;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.OptionalInt;

import net.coljate.list.ListIterator;
import net.coljate.list.MutableList;
import net.coljate.util.Arrays;

/**
 *
 * @author Ollie
 */
public class WrappedDeque<T>
        extends WrappedQueue<T>
        implements MutableList<T> {

    @SafeVarargs
    public static <T> WrappedDeque<T> copyIntoArrayDeque(final T... elements) {
        final java.util.ArrayDeque<T> deque = new ArrayDeque<>(elements.length);
        Arrays.consume(elements, deque::add);
        return new WrappedDeque<>(deque);
    }

    private final java.util.Deque<T> deque;

    protected WrappedDeque(final java.util.Deque<T> deque, final OptionalInt capacity) {
        super(deque, capacity);
        this.deque = deque;
    }

    public WrappedDeque(final java.util.ArrayDeque<T> deque) {
        this(deque, OptionalInt.empty());
    }

    public WrappedDeque(final java.util.LinkedList<T> deque) {
        this(deque, OptionalInt.empty());
    }

    @Override
    public T first() {
        return super.first();
    }

    @Override
    public T last() {
        return deque.peekLast();
    }

    @Override
    public void prefix(final T element) {
        deque.addFirst(element);
    }

    @Override
    public void suffix(final T element) {
        deque.addLast(element);
    }

    @Override
    public boolean removeFirst(final Object element) {
        return deque.removeFirstOccurrence(element);
    }

    @Override
    public boolean removeLast(final Object element) {
        return deque.removeLastOccurrence(element);
    }

    @Override
    public java.util.LinkedList<T> mutableJavaCopy() {
        return this.mutableJavaCopy(i -> new java.util.LinkedList<>());
    }

    @Override
    public WrappedDeque<T> mutableCopy() {
        return new WrappedDeque<>(this.mutableJavaCopy());
    }

    @Override
    public ListIterator<T> iterator() {
        return new WrappedDequeIterator(deque.iterator());
    }

    @Override
    public ListIterator<T> reverseIterator() {
        return new WrappedDequeIterator(deque.descendingIterator());
    }

    private final class WrappedDequeIterator implements ListIterator<T> {

        final Iterator<T> iterator;

        WrappedDequeIterator(final Iterator<T> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException("Not supported yet."); //TODO
        }

        @Override
        public T previous() {
            throw new UnsupportedOperationException("Not supported yet."); //TODO
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            return iterator.next();
        }

    }

}
