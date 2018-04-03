package net.mirwaldt.jcomparison.primitive.mutable.impl;

import net.mirwaldt.jcomparison.primitive.mutable.api.MutableLong;

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
public class DefaultMutableLong implements MutableLong {
    private long value;
    
    public DefaultMutableLong(long value) {
        this.value = value;
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public DefaultMutableLong copy() {
        return new DefaultMutableLong(value);
    }

    @Override
    public void copyTo(MutableLong otherMutable) {
        otherMutable.setValue(value);
    }
    
    @Override
    public Long get() {
        return getValue();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultMutableLong that = (DefaultMutableLong) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }

    @Override
    public String toString() {
        return "DefaultMutableLong{" +
                "value=" + value +
                '}';
    }
}
