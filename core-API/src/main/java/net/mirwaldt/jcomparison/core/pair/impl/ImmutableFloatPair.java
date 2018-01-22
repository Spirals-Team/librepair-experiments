package net.mirwaldt.jcomparison.core.pair.impl;

import net.mirwaldt.jcomparison.core.pair.api.Pair;

import java.io.Serializable;

/**
 * This file is part of the open-source-framework jComparison.
 * Copyright (C) 2015-2017 Michael Mirwaldt.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class ImmutableFloatPair implements Pair<Float>, Serializable {
    private final float leftFloat;
    private final float rightFloat;

    public ImmutableFloatPair(float leftFloat, float rightFloat) {
        this.leftFloat = leftFloat;
        this.rightFloat = rightFloat;
    }

    public float getLeftFloat() {
        return leftFloat;
    }

    public float getRightFloat() {
        return rightFloat;
    }

    @Override
    public Float getLeft() {
        return leftFloat;
    }

    @Override
    public Float getRight() {
        return rightFloat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutableFloatPair that = (ImmutableFloatPair) o;

        if (Float.compare(that.leftFloat, leftFloat) != 0) return false;
        return Float.compare(that.rightFloat, rightFloat) == 0;
    }

    @Override
    public int hashCode() {
        int result = (leftFloat != +0.0f ? Float.floatToIntBits(leftFloat) : 0);
        result = 31 * result + (rightFloat != +0.0f ? Float.floatToIntBits(rightFloat) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" +
                "leftFloat=" + leftFloat +
                ",rightFloat=" + rightFloat +
                ')';
    }
}
