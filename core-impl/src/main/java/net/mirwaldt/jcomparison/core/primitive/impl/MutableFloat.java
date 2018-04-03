package net.mirwaldt.jcomparison.core.primitive.impl;

import net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive;
import net.mirwaldt.jcomparison.core.util.deduplicator.api.Deduplicator;

import java.util.Map;

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
public class MutableFloat implements MutablePrimitive<Float> {
    private float value;
    private final Deduplicator deduplicator;

    public MutableFloat(Deduplicator deduplicator) {
        this.deduplicator = deduplicator;
    }

    public MutableFloat(float value, Deduplicator deduplicator) {
        this.value = value;
        this.deduplicator = deduplicator;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public Float get() {
        return (Float) deduplicator.deduplicate(value);
    }

    @Override
    public MutablePrimitive<Float> copy(Map<Class<?>, MutablePrimitive<?>> cachedMutablePrimitives) {
        final MutableFloat mutableFloat = (MutableFloat) cachedMutablePrimitives.computeIfAbsent(float.class, (type) -> new MutableFloat(deduplicator));
        mutableFloat.setValue(value);
        return mutableFloat;
    }

    @Override
    public boolean isZero() {
        return value == 0f;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MutableFloat that = (MutableFloat) o;

        return Float.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return (value != +0.0f ? Float.floatToIntBits(value) : 0);
    }

    @Override
    public String toString() {
        return "MutableFloat{" +
                "value=" + value +
                '}';
    }
}
