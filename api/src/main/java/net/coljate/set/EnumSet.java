package net.coljate.set;

import java.util.Iterator;

import javax.annotation.Nonnull;

import net.coljate.set.impl.LargeEnumSet;
import net.coljate.set.impl.SmallEnumSet;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author Ollie
 */
public interface EnumSet<E extends Enum<E>> extends Set<E> {

    boolean contains(@Nonnull E e);

    Class<E> enumClass();

    default E[] enumUniverse() {
        return this.enumClass().getEnumConstants();
    }

    @Override
    @SuppressWarnings("unchecked")
    default boolean contains(final Object object) {
        return object != null
                && this.enumClass().isAssignableFrom(object.getClass())
                && this.contains((E) object);
    }

    @Override
    default Iterator<E> iterator() {
        return Iterators.filter(Iterators.of(this.enumUniverse()), this::contains);
    }

    static <E extends Enum<E>> EnumSet<E> noneOf(final Class<E> enumClass) {
        return SmallEnumSet.noneOf(enumClass).orElseGet(() -> LargeEnumSet.noneOf(enumClass));
    }

    @SafeVarargs
    static <E extends Enum<E>> EnumSet<E> of(final E first, final E... rest) {
        final Class<E> enumClass = (Class<E>)first.getClass();
        return SmallEnumSet.supports(enumClass)
                ? SmallEnumSet.of(first, rest)
                : LargeEnumSet.of(first, rest);
    }

    static <E extends Enum<E>> EnumSet<E> allOf(final Class<E> enumClass) {
        return LargeEnumSet.allOf(enumClass);
    }

}
