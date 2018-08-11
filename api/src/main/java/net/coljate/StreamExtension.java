package net.coljate;

import java.util.function.BiConsumer;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.Nonnull;

/**
 *
 * @author Ollie
 * @since 1.0
 */
public interface StreamExtension<T> extends Iterable<T> {

    @Deprecated
    default Stream<T> stream() {
        return this.serialStream();
    }

    @Nonnull
    default Stream<T> stream(final boolean parallel) {
        return parallel ? this.parallelStream() : this.serialStream();
    }

    @Nonnull
    default Stream<T> serialStream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    @Nonnull
    default Stream<T> parallelStream() {
        return StreamSupport.stream(this.spliterator(), true);
    }

    default <C, R> R collect(final Collector<? super T, C, ? extends R> collector) {
        final C container = collector.supplier().get();
        final BiConsumer<C, ? super T> accumulator = collector.accumulator();
        this.forEach(element -> accumulator.accept(container, element));
        return collector.finisher().apply(container);
    }

}
