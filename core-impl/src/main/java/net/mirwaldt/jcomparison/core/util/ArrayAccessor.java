package net.mirwaldt.jcomparison.core.util;

import net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive;
import net.mirwaldt.jcomparison.core.primitive.impl.*;
import net.mirwaldt.jcomparison.core.util.deduplicator.api.Deduplicator;

import java.util.Map;
import java.util.function.Supplier;

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
public class ArrayAccessor {

    public static int getLength(Object arrayObject) {
        final Class<?> arrayType = arrayObject.getClass();
        final Class<?> arrayComponentType = arrayType.getComponentType();
        if (!arrayType.isArray()) {
            throw new IllegalArgumentException("Cannot handle non-array type " + arrayType);
        } else if (arrayComponentType.isPrimitive()) {
            if (arrayComponentType.equals(boolean.class)) {
                return ((boolean[]) arrayObject).length;
            } else if (arrayComponentType.equals(byte.class)) {
                return ((byte[]) arrayObject).length;
            } else if (arrayComponentType.equals(char.class)) {
                return ((char[]) arrayObject).length;
            } else if (arrayComponentType.equals(short.class)) {
                return ((short[]) arrayObject).length;
            } else if (arrayComponentType.equals(int.class)) {
                return ((int[]) arrayObject).length;
            } else if (arrayComponentType.equals(long.class)) {
                return ((long[]) arrayObject).length;
            } else if (arrayComponentType.equals(float.class)) {
                return ((float[]) arrayObject).length;
            } else if (arrayComponentType.equals(double.class)) {
                return ((double[]) arrayObject).length;
            } else {
                throw new IllegalArgumentException("Cannot handle primitive array type " + arrayType);
            }
        } else {
            return ((Object[]) arrayObject).length;
        }
    }

    /**
     * returns the element at an index in an array. 
     * Avoids casting to Object[] if array type is primitive.
     * 
     * @param arrayObject the array object
     * @param index the index
     * @return the element at the index in the array. Primitive values are boxed!
     */
    public static Object getElementAtIndex(Object arrayObject, int index) {
        final Class<?> arrayType = arrayObject.getClass();
        final Class<?> arrayComponentType = arrayType.getComponentType();
        if (!arrayType.isArray()) {
            throw new IllegalArgumentException("Cannot handle non-array type " + arrayType);
        } else if (arrayComponentType.isPrimitive()) {
            if (arrayComponentType.equals(boolean.class)) {
                return ((boolean[]) arrayObject)[index];
            } else if (arrayComponentType.equals(byte.class)) {
                return ((byte[]) arrayObject)[index];
            } else if (arrayComponentType.equals(char.class)) {
                return ((char[]) arrayObject)[index];
            } else if (arrayComponentType.equals(short.class)) {
                return ((short[]) arrayObject)[index];
            } else if (arrayComponentType.equals(int.class)) {
                return ((int[]) arrayObject)[index];
            } else if (arrayComponentType.equals(long.class)) {
                return ((long[]) arrayObject)[index];
            } else if (arrayComponentType.equals(float.class)) {
                return ((float[]) arrayObject)[index];
            } else if (arrayComponentType.equals(double.class)) {
                return ((double[]) arrayObject)[index];
            } else {
                throw new IllegalArgumentException("Cannot handle primitive array type " + arrayType);
            }
        } else {
            return ((Object[]) arrayObject)[index];
        }
    }

    /**
     * avoid (un)boxing by using pooled single element arrays
     *
     * @param arrayObject
     * @param index
     * @param cachedMutablePrimitivesSupplier a cache supplier for the MutablePrimitive instances. If an instance is missing, the instance is created and used.
     * @return the element at index in the array. Primitive values are wrapped into instances of MutablePrimitive. 
     * Reusing instances of MutablePrimitive avoids boxing so far that no new objects are created.
     */
    public static Object getElementAtIndex(Object arrayObject, int index, Supplier<Map<Class<?>, MutablePrimitive<?>>> cachedMutablePrimitivesSupplier, Deduplicator deduplicator) {
        final Class<?> arrayType = arrayObject.getClass();
        final Class<?> arrayComponentType = arrayType.getComponentType();
        if (!arrayType.isArray()) {
            throw new IllegalArgumentException("Cannot handle non-array type " + arrayType);
        } else if (arrayComponentType.isPrimitive()) {
            if (arrayComponentType.equals(boolean.class)) {
                final MutableBoolean mutableByte = (MutableBoolean) cachedMutablePrimitivesSupplier.get().computeIfAbsent(boolean.class, (type) -> new MutableByte(deduplicator));
                mutableByte.setValue(((boolean[]) arrayObject)[index]);
                return mutableByte;
            } else if (arrayComponentType.equals(byte.class)) {
                final MutableByte mutableByte = (MutableByte) cachedMutablePrimitivesSupplier.get().computeIfAbsent(byte.class, (type) -> new MutableByte(deduplicator));
                mutableByte.setValue(((byte[]) arrayObject)[index]);
                return mutableByte;
            } else if (arrayComponentType.equals(char.class)) {
                final MutableChar mutableChar = (MutableChar) cachedMutablePrimitivesSupplier.get().computeIfAbsent(char.class, (type) -> new MutableChar(deduplicator));
                mutableChar.setValue(((char[]) arrayObject)[index]);
                return mutableChar;
            } else if (arrayComponentType.equals(short.class)) {
                final MutableShort mutableShort = (MutableShort) cachedMutablePrimitivesSupplier.get().computeIfAbsent(short.class, (type) -> new MutableShort(deduplicator));
                mutableShort.setValue(((short[]) arrayObject)[index]);
                return mutableShort;
            } else if (arrayComponentType.equals(int.class)) {
                final MutableInt mutableInt = (MutableInt) cachedMutablePrimitivesSupplier.get().computeIfAbsent(int.class, (type) -> new MutableInt(deduplicator));
                mutableInt.setValue(((int[]) arrayObject)[index]);
                return mutableInt;
            } else if (arrayComponentType.equals(long.class)) {
                final MutableLong mutableLong = (MutableLong) cachedMutablePrimitivesSupplier.get().computeIfAbsent(long.class, (type) -> new MutableLong(deduplicator));
                mutableLong.setValue(((long[]) arrayObject)[index]);
                return mutableLong;
            } else if (arrayComponentType.equals(float.class)) {
                final MutableFloat mutableFloat = (MutableFloat) cachedMutablePrimitivesSupplier.get().computeIfAbsent(float.class, (type) -> new MutableFloat(deduplicator));
                mutableFloat.setValue(((float[]) arrayObject)[index]);
                return mutableFloat;
            } else if (arrayComponentType.equals(double.class)) {
                final MutableDouble mutableDouble = (MutableDouble) cachedMutablePrimitivesSupplier.get().computeIfAbsent(double.class, (type) -> new MutableDouble(deduplicator));
                mutableDouble.setValue(((double[]) arrayObject)[index]);
                return mutableDouble;
            } else {
                throw new IllegalArgumentException("Cannot handle primitive array type '" + arrayComponentType + "'.");
            }
        } else {
            return ((Object[]) arrayObject)[index];
        }
    }
}
