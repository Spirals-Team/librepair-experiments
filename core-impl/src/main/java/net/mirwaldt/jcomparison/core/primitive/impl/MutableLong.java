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
public class MutableLong implements MutablePrimitive<Long> {
    private long value;
    private final Deduplicator deduplicator;
    
    public MutableLong(Deduplicator deduplicator) {
        this.deduplicator = deduplicator;
    }

    public MutableLong(long value, Deduplicator deduplicator) {
        this.value = value;
        this.deduplicator = deduplicator;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public Long get() {
        return (Long) deduplicator.deduplicate(value);
    }

    @Override
    public MutablePrimitive<Long> copy(Map<Class<?>, MutablePrimitive<?>> cachedMutablePrimitives) {
        final MutableLong mutableLong = (MutableLong) cachedMutablePrimitives.computeIfAbsent(long.class, (type) -> new MutableLong(deduplicator));
        mutableLong.setValue(value);
        return mutableLong;
    }

    @Override
    public boolean isZero() {
        return value == 0L;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MutableLong that = (MutableLong) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }

    @Override
    public String toString() {
        return "MutableLong{" +
                "value=" + value +
                '}';
    }
}
