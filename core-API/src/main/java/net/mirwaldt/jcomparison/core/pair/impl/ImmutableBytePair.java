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
public class ImmutableBytePair implements Pair<Byte>, Serializable {
    private final byte leftByte;
    private final byte rightByte;

    public ImmutableBytePair(byte leftByte, byte rightByte) {
        this.leftByte = leftByte;
        this.rightByte = rightByte;
    }

    public byte getLeftByte() {
        return leftByte;
    }

    public byte getRightByte() {
        return rightByte;
    }

    @Override
    public Byte getLeft() {
        return leftByte;
    }

    @Override
    public Byte getRight() {
        return rightByte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutableBytePair that = (ImmutableBytePair) o;

        if (leftByte != that.leftByte) return false;
        return rightByte == that.rightByte;
    }

    @Override
    public int hashCode() {
        int result = (int) leftByte;
        result = 31 * result + (int) rightByte;
        return result;
    }

    @Override
    public String toString() {
        return "(" +
                "leftByte=" + leftByte +
                ",rightByte=" + rightByte +
                ')';
    }
}
