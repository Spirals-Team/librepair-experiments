package net.coljate.list.impl;

import java.util.Iterator;
import java.util.OptionalInt;

import net.coljate.collection.impl.MutableWrappedCollection;
import net.coljate.list.ImmutableArray;
import net.coljate.list.Queue;

/**
 * Queue wrapper around a {@link java.util.LinkedHashSet}.
 *
 * Enqueue operations will always put an element on the tail, moving it from elsewhere in the queue if necessary.
 *
 * @author Ollie
 */
public class WrappedLinkedHashSetQueue<T>
        extends MutableWrappedCollection<T>
        implements Queue<T> {

    public WrappedLinkedHashSetQueue() {
        this(new java.util.LinkedHashSet<>());
    }

    public WrappedLinkedHashSetQueue(final java.util.LinkedHashSet<T> set) {
        super(set);
    }

    @Override
    public Element<T> peek() {
        final Iterator<T> iterator = this.iterator();
        return iterator.hasNext()
                ? Element.of(iterator.next())
                : null;
    }

    @Override
    public Element<T> poll() {
        final Iterator<T> iterator = this.iterator();
        if (iterator.hasNext()) {
            final T next = iterator.next();
            iterator.remove();
            return Element.of(next);
        } else {
            return null;
        }
    }

    @Override
    public boolean add(final T element) {
        super.removeFirst(element);
        return super.add(element);
    }

    @Override
    public OptionalInt capacity() {
        return OptionalInt.empty();
    }

    @Override
    public java.util.LinkedHashSet<T> mutableJavaCopy() {
        return super.mutableJavaCopy(java.util.LinkedHashSet::new); //FIXME this only copies elements, not attributes!
    }

    @Override
    public WrappedLinkedHashSetQueue<T> mutableCopy() {
        return new WrappedLinkedHashSetQueue<>(this.mutableJavaCopy());
    }

    @Override
    public ImmutableArray<T> immutableCopy() {
        return Queue.super.immutableCopy();
    }

}
