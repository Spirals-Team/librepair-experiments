package net.coljate.collection;

import net.coljate.collection.impl.EmptyCollection;
import net.coljate.list.ImmutableList;
import net.coljate.set.ImmutableSet;
import net.coljate.util.iterator.UnmodifiableIterator;

import javax.annotation.concurrent.Immutable;
import java.util.Spliterator;
import java.util.Spliterators;

/**
 * @author Ollie
 */
@Immutable
public interface ImmutableCollection<T> extends Collection<T> {

    @Override
    UnmodifiableIterator<T> iterator();

    @Deprecated
    @Override
    default ImmutableCollection<T> immutableCopy() {
        return this;
    }

    @Override
    default Spliterator<T> spliterator() {
        return Spliterators.spliterator(this.iterator(), this.count(), Spliterator.SIZED | Spliterator.IMMUTABLE);
    }

    static <T> EmptyCollection<T> of() {
        return EmptyCollection.instance();
    }

    static <T> Singleton<T> of(final T element) {
        return ImmutableSet.of(element);
    }

    static <T> ImmutableCollection<T> copyOf(final Collection<? extends T> collection) {
        return ImmutableList.copyOf(collection);
    }

}
