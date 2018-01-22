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
public class ImmutableDoublePair implements Pair<Double>, Serializable {
    private final double leftDouble;
    private final double rightDouble;

    public ImmutableDoublePair(double leftDouble, double rightDouble) {
        this.leftDouble = leftDouble;
        this.rightDouble = rightDouble;
    }

    public double getLeftDouble() {
        return leftDouble;
    }

    public double getRightDouble() {
        return rightDouble;
    }

    @Override
    public Double getLeft() {
        return leftDouble;
    }

    @Override
    public Double getRight() {
        return rightDouble;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutableDoublePair that = (ImmutableDoublePair) o;

        if (Double.compare(that.leftDouble, leftDouble) != 0) return false;
        return Double.compare(that.rightDouble, rightDouble) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(leftDouble);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(rightDouble);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "(" +
                "leftDouble=" + leftDouble +
                ",rightDouble=" + rightDouble +
                ')';
    }
}
