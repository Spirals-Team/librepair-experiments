package net.mirwaldt.jcomparison.core.primitive.impl;

import net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive;
import net.mirwaldt.jcomparison.core.util.deduplicator.api.Deduplicator;

import java.util.Map;

public class MutableBoolean implements MutablePrimitive<Boolean> {
    private boolean value;

    private final Deduplicator deduplicator;

    public MutableBoolean(Deduplicator deduplicator) {
        this.deduplicator = deduplicator;
    }

    public MutableBoolean(boolean value, Deduplicator deduplicator) {
        this.value = value;
        this.deduplicator = deduplicator;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean get() {
        return (Boolean) deduplicator.deduplicate(value);
    }

    @Override
    public MutablePrimitive<Boolean> copy(Map<Class<?>, MutablePrimitive<?>> cachedMutablePrimitives) {
        final MutableBoolean mutableBoolean = (MutableBoolean) cachedMutablePrimitives.computeIfAbsent(boolean.class, (type) -> new MutableBoolean(deduplicator));
        mutableBoolean.setValue(value);
        return mutableBoolean;
    }

    @Override
    public boolean isZero() {
        throw new UnsupportedOperationException("A boolean cannot be zero. It can be either true or false.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MutableBoolean that = (MutableBoolean) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return (value ? 1 : 0);
    }

    @Override
    public String toString() {
        return "MutableBoolean{" +
                "value=" + value +
                '}';
    }
}
