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
public class ImmutableShortPair implements Pair<Short>, Serializable {
    private final short leftShort;
    private final short rightShort;

    public ImmutableShortPair(short leftShort, short rightShort) {
        this.leftShort = leftShort;
        this.rightShort = rightShort;
    }

    public short getLeftShort() {
        return leftShort;
    }

    public short getRightShort() {
        return rightShort;
    }

    @Override
    public Short getLeft() {
        return leftShort;
    }

    @Override
    public Short getRight() {
        return rightShort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutableShortPair that = (ImmutableShortPair) o;

        if (leftShort != that.leftShort) return false;
        return rightShort == that.rightShort;
    }

    @Override
    public int hashCode() {
        int result = (int) leftShort;
        result = 31 * result + (int) rightShort;
        return result;
    }

    @Override
    public String toString() {
        return "(" +
                "leftShort=" + leftShort +
                ",rightShort=" + rightShort +
                ')';
    }
}
