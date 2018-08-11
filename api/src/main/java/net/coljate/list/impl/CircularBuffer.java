package net.coljate.list.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.OptionalInt;

import net.coljate.list.Queue;
import net.coljate.util.Arrays;
import net.coljate.util.functions.Functions;

/**
 *
 * @author Ollie
 */
public class CircularBuffer<T> implements Queue<T> {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> CircularBuffer<T> create(final int capacity) {
        return new CircularBuffer<>(new Element[capacity + 1]);
    }

    private final Element<T>[] array;
    private int read, write;

    protected CircularBuffer(final Element<T>[] array) {
        this(array, 0, 0);
    }

    protected CircularBuffer(final Element<T>[] array, final int read, final int write) {
        if (array.length <= 1) {
            throw new IllegalArgumentException("Cannot create a queue with zero capacity!");
        }
        this.array = array;
        this.read = read;
        this.write = write;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Element<T> peek() {
        return this.isEmpty()
                ? null
                : array[read];
    }

    @Override
    public Element<T> poll() {
        return this.isEmpty()
                ? null
                : array[this.nextRead()];
    }

    private int nextRead() {
        return (read++) % array.length;
    }

    @Override
    public OptionalInt capacity() {
        return OptionalInt.of(array.length - 1);
    }

    @Override
    public boolean add(final T element) {
        if (this.isFull()) {
            return false;
        } else {
            array[this.nextWrite()] = Element.of(element);
            return true;
        }
    }

    private int nextWrite() {
        return (write++) % array.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new CircularBufferIterator();
    }

    @Override
    public void clear() {
        read = write = 0;
        //Also drop references
        Arrays.update(array, e -> null);
    }

    @Override
    public int count() {
        return write - read;
    }

    @Override
    public boolean isEmpty() {
        return read == write;
    }

    @Override
    public boolean isFull() {
        return read == (write + 1) % array.length;
    }

    @Override
    public CircularBuffer<T> mutableCopy() {
        return new CircularBuffer<>(Arrays.copy(array), read, write);
    }

    private final class CircularBufferIterator implements Iterator<T> {

        int index = read;

        @Override
        public boolean hasNext() {
            return index < write;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return Functions.ifNonNull(array[(index++) % array.length], Element::value);
        }

    }

}
