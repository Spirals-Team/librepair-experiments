package net.mirwaldt.jcomparison.core.util.view.impl;

import net.mirwaldt.jcomparison.core.util.view.api.IntRange;

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
public class ImmutableIntRange implements IntRange {
    private final int startIndex;
    private final int endIndex;

    public ImmutableIntRange(int startIndex, int endIndex) throws IllegalArgumentException {
        if(startIndex < 0) {
            throw new IllegalArgumentException("Start index must be greater or equal 0. Start index is '" + startIndex +"'.");
        } else if(endIndex < startIndex) {
            throw new IllegalArgumentException("Start index must lie before or at end index. Start index is '" + startIndex +"' and end index '" + endIndex + "'.");
        }

        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutableIntRange that = (ImmutableIntRange) o;

        if (startIndex != that.startIndex) return false;
        return endIndex == that.endIndex;
    }

    @Override
    public int hashCode() {
        int result = startIndex;
        result = 31 * result + endIndex;
        return result;
    }
}
