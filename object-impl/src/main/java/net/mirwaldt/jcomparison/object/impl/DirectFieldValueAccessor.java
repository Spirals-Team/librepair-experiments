package net.mirwaldt.jcomparison.object.impl;

import net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive;
import net.mirwaldt.jcomparison.core.primitive.impl.*;
import net.mirwaldt.jcomparison.core.exception.CannotAccessFieldException;
import net.mirwaldt.jcomparison.core.util.deduplicator.api.Deduplicator;
import net.mirwaldt.jcomparison.object.api.PrimitiveSupportingFieldValueAccessor;

import java.lang.reflect.Field;
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
public class DirectFieldValueAccessor implements PrimitiveSupportingFieldValueAccessor {

    @Override
    public Object accessFieldValue(Class<?> type, Field targetField, Object targetObject) throws CannotAccessFieldException {
        return accessFieldValue(type, targetField, targetObject, null, null);
    }

    @Override
    public Object accessFieldValue(Class<?> type, Field targetField, Object targetObject, Supplier<Map<Class<?>, MutablePrimitive<?>>> cachedMutablePrimitivesSupplier, Deduplicator deduplicator) throws CannotAccessFieldException {
        try {
            targetField.setAccessible(true);
            final Class<?> typeOfField = targetField.getType();
            if (cachedMutablePrimitivesSupplier != null && typeOfField.isPrimitive()) {
                if (typeOfField.equals(boolean.class)) {
                    final MutableBoolean mutableByte = (MutableBoolean) cachedMutablePrimitivesSupplier.get().computeIfAbsent(boolean.class, (primitiveType) -> new MutableBoolean(deduplicator));
                    mutableByte.setValue(targetField.getBoolean(targetObject));
                    return mutableByte;
                } else if (typeOfField.equals(byte.class)) {
                    final MutableByte mutableByte = (MutableByte) cachedMutablePrimitivesSupplier.get().computeIfAbsent(byte.class, (primitiveType) -> new MutableByte(deduplicator));
                    mutableByte.setValue(targetField.getByte(targetObject));
                    return mutableByte;
                } else if (typeOfField.equals(char.class)) {
                    final MutableChar mutableChar = (MutableChar) cachedMutablePrimitivesSupplier.get().computeIfAbsent(char.class, (primitiveType) -> new MutableChar(deduplicator));
                    mutableChar.setValue(targetField.getChar(targetObject));
                    return mutableChar;
                } else if (typeOfField.equals(short.class)) {
                    final MutableShort mutableShort = (MutableShort) cachedMutablePrimitivesSupplier.get().computeIfAbsent(short.class, (primitiveType) -> new MutableShort(deduplicator));
                    mutableShort.setValue(targetField.getShort(targetObject));
                    return mutableShort;
                } else if (typeOfField.equals(int.class)) {
                    final MutableInt mutableInt = (MutableInt) cachedMutablePrimitivesSupplier.get().computeIfAbsent(int.class, (primitiveType) -> new MutableInt(deduplicator));
                    mutableInt.setValue(targetField.getInt(targetObject));
                    return mutableInt;
                } else if (typeOfField.equals(long.class)) {
                    final MutableLong mutableLong = (MutableLong) cachedMutablePrimitivesSupplier.get().computeIfAbsent(long.class, (primitiveType) -> new MutableLong(deduplicator));
                    mutableLong.setValue(targetField.getLong(targetObject));
                    return mutableLong;
                } else if (typeOfField.equals(float.class)) {
                    final MutableFloat mutableFloat = (MutableFloat) cachedMutablePrimitivesSupplier.get().computeIfAbsent(float.class, (primitiveType) -> new MutableFloat(deduplicator));
                    mutableFloat.setValue(targetField.getFloat(targetObject));
                    return mutableFloat;
                } else if (typeOfField.equals(double.class)) {
                    final MutableDouble mutableDouble = (MutableDouble) cachedMutablePrimitivesSupplier.get().computeIfAbsent(double.class, (primitiveType) -> new MutableDouble(deduplicator));
                    mutableDouble.setValue(targetField.getDouble(targetObject));
                    return mutableDouble;
                } else {
                    throw new IllegalArgumentException("Cannot handle primitive type '" + typeOfField + "'.");
                }
            } else {
                return targetField.get(targetObject);
            }
        } catch (IllegalAccessException e) {
            throw new CannotAccessFieldException("Illegal access on field '" + targetField + "'.", e, type, targetField, targetObject);
        }
    }
}
