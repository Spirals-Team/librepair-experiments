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
public class ImmutableIntPair implements Pair<Integer>, Serializable {
    private final int leftInt;
    private final int rightInt;

    public ImmutableIntPair(int leftInt, int rightInt) {
        this.leftInt = leftInt;
        this.rightInt = rightInt;
    }

    public int getLeftInt() {
        return leftInt;
    }

    public int getRightInt() {
        return rightInt;
    }

    @Override
    public Integer getLeft() {
        return leftInt;
    }

    @Override
    public Integer getRight() {
        return rightInt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutableIntPair that = (ImmutableIntPair) o;

        if (leftInt != that.leftInt) return false;
        return rightInt == that.rightInt;
    }

    @Override
    public int hashCode() {
        int result = leftInt;
        result = 31 * result + rightInt;
        return result;
    }

    @Override
    public String toString() {
        return "(" +
                "leftInt=" + leftInt +
                ",rightInt=" + rightInt +
                ')';
    }
}
