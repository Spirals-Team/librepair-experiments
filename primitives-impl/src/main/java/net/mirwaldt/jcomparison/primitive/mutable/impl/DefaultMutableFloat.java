package net.mirwaldt.jcomparison.primitive.mutable.impl;

import net.mirwaldt.jcomparison.primitive.mutable.api.MutableFloat;

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
public class DefaultMutableFloat implements MutableFloat {
    private float value;
    
    public DefaultMutableFloat(float value) {
        this.value = value;
    }

    @Override
    public float getValue() {
        return value;
    }

    @Override
    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public DefaultMutableFloat copy() {
        return new DefaultMutableFloat(value);
    }

    @Override
    public void copyTo(MutableFloat otherMutable) {
        otherMutable.setValue(value);
    }

    @Override
    public Float get() {
        return getValue();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DefaultMutableFloat that = (DefaultMutableFloat) o;

        return Float.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return (value != +0.0f ? Float.floatToIntBits(value) : 0);
    }

    @Override
    public String toString() {
        return "DefaultMutableFloat{" +
                "value=" + value +
                '}';
    }
}
