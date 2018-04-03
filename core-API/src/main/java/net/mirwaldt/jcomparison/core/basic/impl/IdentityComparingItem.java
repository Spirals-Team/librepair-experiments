package net.mirwaldt.jcomparison.core.basic.impl;

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
 *   
 * This class ensures that only the identity operator is used for equals
 * and the System.identityHashCode(...) is used in hash-based collections.
 *
 * The identity operator and System.identityHashCode are much cheaper
 * than most equals-implementations and hashCode-implementations
 */
public class IdentityComparingItem {
    private Object object;

    public IdentityComparingItem(Object object) {
        this.object = object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdentityComparingItem that = (IdentityComparingItem) o;

        return object == that.object;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(object);
    }
}
