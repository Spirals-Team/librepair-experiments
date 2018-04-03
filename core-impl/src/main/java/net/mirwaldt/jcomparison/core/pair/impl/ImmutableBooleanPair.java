package net.mirwaldt.jcomparison.core.pair.impl;

import net.mirwaldt.jcomparison.core.pair.api.Pair;

import java.io.Serializable;

public class ImmutableBooleanPair implements Pair<Boolean>, Serializable {
    private final boolean leftBoolean;
    private final boolean rightBoolean;

    public ImmutableBooleanPair(boolean leftBoolean, boolean rightBoolean) {
        this.leftBoolean = leftBoolean;
        this.rightBoolean = rightBoolean;
    }

    public boolean getLeftBoolean() {
        return leftBoolean;
    }

    public boolean getRightBoolean() {
        return rightBoolean;
    }

    @Override
    public Boolean getLeft() {
        return leftBoolean;
    }

    @Override
    public Boolean getRight() {
        return rightBoolean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutableBooleanPair that = (ImmutableBooleanPair) o;

        if (leftBoolean != that.leftBoolean) return false;
        return rightBoolean == that.rightBoolean;
    }

    @Override
    public int hashCode() {
        int result = (leftBoolean ? 1 : 0);
        result = 31 * result + (rightBoolean ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "*" +
                "leftBoolean=" + leftBoolean +
                ",rightBoolean=" + rightBoolean +
                '(';
    }
}
