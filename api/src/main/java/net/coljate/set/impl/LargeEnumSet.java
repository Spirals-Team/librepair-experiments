package net.coljate.set.impl;

import java.util.Objects;

import net.coljate.set.AbstractSet;
import net.coljate.set.EnumSet;
import net.coljate.util.Arrays;

/**
 *
 * @author Ollie
 */
public class LargeEnumSet<E extends Enum<E>>
        extends AbstractSet<E>
        implements EnumSet<E> {

    static final byte MISSING = 0, PRESENT = 1;

    public static <E extends Enum<E>> LargeEnumSet<E> noneOf(final Class<E> enumClass) {
        final E[] enums = enumClass.getEnumConstants();
        final byte[] values = new byte[enums.length];
        return new LargeEnumSet<>(enumClass, values);
    }

    @SafeVarargs
    public static <E extends Enum<E>> LargeEnumSet<E> of(final E first, final E... others) {
        @SuppressWarnings("unchecked")
        final Class<E> enumClass = (Class<E>) first.getClass();
        final E[] enums = enumClass.getEnumConstants();
        final byte[] values = new byte[enums.length];
        values[first.ordinal()] = PRESENT;
        Arrays.consume(others, e -> values[e.ordinal()] = PRESENT);
        return new LargeEnumSet<>(enumClass, values);
    }

    public static <E extends Enum<E>> LargeEnumSet<E> allOf(final Class<E> enumClass) {
        final E[] enums = enumClass.getEnumConstants();
        final byte[] values = new byte[enums.length];
        java.util.Arrays.fill(values, PRESENT);
        return new LargeEnumSet<>(enumClass, values);
    }

    private final Class<E> enumClass;
    final byte[] values;

    protected LargeEnumSet(final Class<E> enumClass, final byte[] values) {
        this.enumClass = Objects.requireNonNull(enumClass);
        this.values = values;
    }

    @Override
    public boolean contains(final E e) {
        return values[e.ordinal()] == PRESENT;
    }

    @Override
    public Class<E> enumClass() {
        return enumClass;
    }

    @Override
    public boolean contains(final Object object) {
        return EnumSet.super.contains(object);
    }

    @Override
    public MutableLargeEnumSet<E> mutableCopy() {
        return new MutableLargeEnumSet<>(enumClass, Arrays.copy(values));
    }

}
