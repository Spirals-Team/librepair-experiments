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
public class ImmutableCharPair implements Pair<Character>, Serializable {
    private final char leftChar;
    private final char rightChar;

    public ImmutableCharPair(char leftChar, char rightChar) {
        this.leftChar = leftChar;
        this.rightChar = rightChar;
    }

    public char getLeftChar() {
        return leftChar;
    }

    public char getRightChar() {
        return rightChar;
    }

    @Override
    public Character getLeft() {
        return leftChar;
    }

    @Override
    public Character getRight() {
        return rightChar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutableCharPair that = (ImmutableCharPair) o;

        if (leftChar != that.leftChar) return false;
        return rightChar == that.rightChar;
    }

    @Override
    public int hashCode() {
        int result = (int) leftChar;
        result = 31 * result + (int) rightChar;
        return result;
    }

    @Override
    public String toString() {
        return "(" +
                "leftChar=" + leftChar +
                ",rightChar=" + rightChar +
                ')';
    }
}
