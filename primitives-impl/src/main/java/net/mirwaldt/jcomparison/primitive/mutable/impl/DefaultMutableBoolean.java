package net.mirwaldt.jcomparison.primitive.mutable.impl;

import net.mirwaldt.jcomparison.primitive.mutable.api.MutableBoolean;

public class DefaultMutableBoolean implements MutableBoolean {
    private boolean value;

    public DefaultMutableBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public boolean getValue() {
        return value;
    }

    @Override
    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public DefaultMutableBoolean copy() {
        return new DefaultMutableBoolean(value);
    }

    @Override
    public void copyTo(MutableBoolean otherMutable) {
        otherMutable.setValue(value);
    }

    @Override
    public Boolean get() {
        return getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultMutableBoolean that = (DefaultMutableBoolean) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return (value ? 1 : 0);
    }

    @Override
    public String toString() {
        return "DefaultMutableBoolean{" +
                "value=" + value +
                '}';
    }
}
