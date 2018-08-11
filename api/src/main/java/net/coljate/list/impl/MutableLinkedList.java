package net.coljate.list.impl;

import java.util.NoSuchElementException;

import javax.annotation.Nullable;

import net.coljate.list.AbstractList;
import net.coljate.list.List;
import net.coljate.list.ListIterator;
import net.coljate.list.MutableList;
import net.coljate.util.Arrays;
import net.coljate.util.functions.Functions;

/**
 *
 * @author Ollie
 */
public class MutableLinkedList<T>
        extends AbstractList<T>
        implements MutableList<T> {

    public static <T> MutableLinkedList<T> create() {
        return new MutableLinkedList<>();
    }

    @SafeVarargs
    public static <T> MutableLinkedList<T> copyOf(final T... elements) {
        final MutableLinkedList<T> list = new MutableLinkedList<>();
        Arrays.consume(elements, list::suffix);
        return list;
    }

    public static <T> MutableLinkedList<T> copyOf(final Iterable<? extends T> iterable) {
        final MutableLinkedList<T> list = new MutableLinkedList<>();
        iterable.forEach(list::suffix);
        return list;
    }

    @Nullable
    private Node first, last;

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public T first() {
        return Functions.ifNonNull(first, Node::value);
    }

    @Override
    public T last() {
        return Functions.ifNonNull(last, Node::value);
    }

    @Override
    public void prefix(final T element) {
        first = new Node(element).insertBefore(first);
    }

    @Override
    public void suffix(final T element) {
        last = new Node(element).insertAfter(last);
    }

    @Override
    public void clear() {
        first = last = null;
    }

    @Override
    public List<T> reversedCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public ListIterator<T> iterator() {
        return new LinkedListForwardIterator();
    }

    @Override
    public ListIterator<T> reverseIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MutableLinkedList<T> mutableCopy() {
        return MutableLinkedList.copyOf(this);
    }

    private final class Node {

        private Node prior, next;
        private T value;

        Node(final T value) {
            this.value = value;
        }

        T value() {
            return value;
        }

        Node insertBefore(final Node that) {
            if (that == null) {
                last = this;
            } else {
                final Node lastPrior = that.prior;
                if (lastPrior != null) {
                    lastPrior.setNext(this);
                }
                this.setNext(that);
            }
            return this;
        }

        Node insertAfter(final Node that) {
            if (that == null) {
                first = this;
            } else {
                final Node lastNext = that.next;
                that.setNext(this);
                this.setNext(lastNext);
            }
            return this;
        }

        void setNext(final Node that) {
            this.next = that;
            if (that == null) {
                last = this;
            } else {
                that.prior = this;
            }
        }

        void setPrior(final Node that) {
            this.prior = that;
            if (that == null) {
                first = this;
            } else {
                that.next = this;
            }
        }

        void delete() {
            if (prior != null) {
                prior.setNext(next);
            } else if (next != null) {
                next.setPrior(prior);
            } else {
                first = last = null;
            }
        }

    }

    private final class LinkedListForwardIterator implements ListIterator<T> {

        private Node current = first, previous = null;

        @Override
        public boolean hasPrevious() {
            return current != null && current.prior != null;
        }

        @Override
        public T previous() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            current = current.prior;
            return current.value();
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            final T nextValue = current.value;
            previous = current;
            current = current.next;
            return nextValue;
        }

        @Override
        public void remove() {
            if (previous == null) {
                throw new NoSuchElementException();
            }
            previous.delete();
        }

    }

}
