package net.coljate.set.impl;

import java.util.Objects;
import java.util.Optional;

import net.coljate.set.AbstractSet;
import net.coljate.set.EnumSet;

/**
 *
 * @author Ollie
 * @see java.util.RegularEnumSet
 */
public class SmallEnumSet<E extends Enum<E>>
        extends AbstractSet<E>
        implements EnumSet<E> {

    public static boolean supports(final Class<?> enumClass) {
        final Object[] constants = enumClass.getEnumConstants();
        return constants != null
                && constants.length <= 64;
    }

    public static <E extends Enum<E>> Optional<EnumSet<E>> noneOf(final Class<E> enumClass) {
        return supports(enumClass)
                ? Optional.of(new SmallEnumSet<>(enumClass, 0L))
                : Optional.empty();
    }

    @SafeVarargs
    public static <E extends Enum<E>> SmallEnumSet<E> of(final E first, final E... rest) {
        long e = 1L << first.ordinal();
        for (final E element : rest) {
            e &= 1L << element.ordinal();
        }
        return new SmallEnumSet<>((Class<E>) first.getClass(), e);
    }

    private final Class<E> enumClass;
    private final long value;

    protected SmallEnumSet(final Class<E> enumClass, final long initialValue) {
        this.enumClass = Objects.requireNonNull(enumClass);
        if (!supports(enumClass)) {
            throw new IllegalArgumentException("Too many enum values in " + enumClass);
        }
        this.value = initialValue;
    }

    @Override
    public Class<E> enumClass() {
        return enumClass;
    }

    protected long value() {
        return value;
    }

    @Override
    public boolean contains(final E e) {
        return (value & (1 << e.ordinal())) != 0;
    }

    @Override
    public boolean contains(final Object object) {
        return EnumSet.super.contains(object);
    }

}
