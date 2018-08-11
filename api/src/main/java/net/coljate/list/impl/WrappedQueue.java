package net.coljate.list.impl;

import java.util.ArrayDeque;
import java.util.OptionalInt;

import net.coljate.collection.impl.MutableWrappedCollection;
import net.coljate.list.ImmutableArray;
import net.coljate.list.Queue;

/**
 *
 * @author Ollie
 */
public class WrappedQueue<T>
        extends MutableWrappedCollection<T>
        implements Queue<T> {

    private final java.util.Queue<T> queue;
    private final OptionalInt capacity;

    protected WrappedQueue(final java.util.Queue<T> queue, final OptionalInt capacity) {
        super(queue);
        this.queue = queue;
        this.capacity = capacity;
    }

    @Override
    public boolean add(final T element) {
        return queue.offer(element);
    }

    @Override
    public Element<T> peek() {
        return queue.isEmpty()
                ? null
                : Element.of(this.nativePeek());
    }

    protected T nativePeek() {
        return queue.peek();
    }

    @Override
    public Element<T> poll() {
        return queue.isEmpty()
                ? null
                : Element.of(this.nativePoll());
    }

    protected T nativePoll() {
        return queue.poll();
    }

    @Override
    public OptionalInt capacity() {
        return capacity;
    }

    @Override
    public T dequeue() {
        return queue.remove();
    }

    @Override
    @Deprecated
    public T element() {
        return queue.element();
    }

    @Override
    public WrappedQueue<T> mutableCopy() {
        return new WrappedQueue<>(new ArrayDeque<>(queue), OptionalInt.empty());
    }
    
    @Override
    public ImmutableArray<T> immutableCopy() {
        return Queue.super.immutableCopy();
    }

}
