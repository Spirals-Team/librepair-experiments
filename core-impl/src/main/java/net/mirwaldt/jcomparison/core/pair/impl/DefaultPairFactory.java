package net.mirwaldt.jcomparison.core.pair.impl;

import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.api.PairFactory;
import net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive;
import net.mirwaldt.jcomparison.core.primitive.impl.*;

import java.util.function.Function;

/**
 * This file is part of the open-source-framework jComparison.
 * Copyright (C) 2015-2017 Michael Mirwaldt.
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class DefaultPairFactory implements PairFactory {
    
    private final Function<Object, Object> arrayWrapperFunction;

    public DefaultPairFactory(Function<Object, Object> arrayWrapperFunction) {
        this.arrayWrapperFunction = arrayWrapperFunction;
    }

    /**
     * returns a pair instance. Handles primitive values without boxing them.
     *
     * @param left  left object
     * @param right right object
     * @return a pair instance.
     * If both parameters are instances of MutablePrimitive and have the same type,
     * special pair types are used which avoid boxing.
     */
    public Pair<?> createPair(Object left, Object right) throws Exception {
        return createPair(left, right, null);
    }

    @Override
    public Pair<?> createPair(Object left, Object right, Class<?> primitiveNumberType) throws Exception {
        if (left == null || right == null) {
            return createDefaultPair(left, right);
        } else if (left instanceof MutablePrimitive && left.getClass().equals(right.getClass())) {
            if (left instanceof MutableBoolean) {
                final MutableBoolean leftMutableBoolean = (MutableBoolean) left;
                final MutableBoolean rightMutableBoolean = (MutableBoolean) right;
                return new ImmutableBooleanPair(leftMutableBoolean.getValue(), rightMutableBoolean.getValue());
            } else if (left instanceof MutableByte) {
                final MutableByte leftMutableByte = (MutableByte) left;
                final MutableByte rightMutableByte = (MutableByte) right;
                return new ImmutableBytePair(leftMutableByte.getValue(), rightMutableByte.getValue());
            } else if (left instanceof MutableChar) {
                final MutableChar leftMutableChar = (MutableChar) left;
                final MutableChar rightMutableChar = (MutableChar) right;
                return new ImmutableCharPair(leftMutableChar.getValue(), rightMutableChar.getValue());
            } else if (left instanceof MutableShort) {
                final MutableShort leftMutableShort = (MutableShort) left;
                final MutableShort rightMutableShort = (MutableShort) right;
                return new ImmutableShortPair(leftMutableShort.getValue(), rightMutableShort.getValue());
            } else if (left instanceof MutableInt) {
                final MutableInt leftMutableInt = (MutableInt) left;
                final MutableInt rightMutableInt = (MutableInt) right;
                return new ImmutableIntPair(leftMutableInt.getValue(), rightMutableInt.getValue());
            } else if (left instanceof MutableLong) {
                final MutableLong leftMutableLong = (MutableLong) left;
                final MutableLong rightMutableLong = (MutableLong) right;
                return new ImmutableLongPair(leftMutableLong.getValue(), rightMutableLong.getValue());
            } else if (left instanceof MutableFloat) {
                final MutableFloat leftMutableFloat = (MutableFloat) left;
                final MutableFloat rightMutableFloat = (MutableFloat) right;
                return new ImmutableFloatPair(leftMutableFloat.getValue(), rightMutableFloat.getValue());
            } else if (left instanceof MutableDouble) {
                final MutableDouble leftMutableDouble = (MutableDouble) left;
                final MutableDouble rightMutableDouble = (MutableDouble) right;
                return new ImmutableDoublePair(leftMutableDouble.getValue(), rightMutableDouble.getValue());
            } else {
                throw new IllegalArgumentException("Cannot handle type '" + left.getClass().getName() + "'.");
            }
        } else if(left.getClass().equals(right.getClass())) {
            if (left instanceof Number) {
                if (Byte.class.equals(primitiveNumberType)) {
                    return new ImmutableBytePair(((Number) left).byteValue(), ((Number) right).byteValue());
                } else if (Short.class.equals(primitiveNumberType)) {
                    return new ImmutableShortPair(((Number) left).shortValue(), ((Number) right).shortValue());
                } else if (Integer.class.equals(primitiveNumberType)) {
                    return new ImmutableIntPair(((Number) left).intValue(), ((Number) right).intValue());
                } else if (Long.class.equals(primitiveNumberType)) {
                    return new ImmutableLongPair(((Number) left).longValue(), ((Number) right).longValue());
                } else if (Float.class.equals(primitiveNumberType)) {
                    return new ImmutableFloatPair(((Number) left).floatValue(), ((Number) right).floatValue());
                } else if (Double.class.equals(primitiveNumberType)) {
                    return new ImmutableDoublePair(((Number) left).doubleValue(), ((Number) right).doubleValue());
                } else if (left instanceof Byte) {
                    return new ImmutableBytePair((Byte) left, (Byte) right);
                } else if (left instanceof Short) {
                    return new ImmutableShortPair((Short) left, (Short) right);
                } else if (left instanceof Integer) {
                    return new ImmutableIntPair((Integer) left, (Integer) right);
                } else if (left instanceof Long) {
                    return new ImmutableLongPair((Long) left, (Long) right);
                } else if (left instanceof Float) {
                    return new ImmutableFloatPair((Float) left, (Float) right);
                } else if (left instanceof Double) {
                    return new ImmutableDoublePair((Double) left, (Double) right);
                } else {
                    return createDefaultPair(left, right);
                }
            } else if (left instanceof Boolean) {
                return new ImmutableBooleanPair((Boolean) left, (Boolean) right);
            } else if (left instanceof Character) {
                return new ImmutableCharPair((Character) left, (Character) right);
            } else {
                return createDefaultPair(left, right);
            }
        } else {
            return createDefaultPair(left, right);
        }
    }

    private Pair<?> createDefaultPair(Object left, Object right) {
        final Object finalLeftObject;
        if (left instanceof MutablePrimitive) {
            final MutablePrimitive leftMutablePrimitive = (MutablePrimitive) left;
            finalLeftObject = leftMutablePrimitive.get();
        } else {
            finalLeftObject = left;
        }

        final Object finalRightObject;
        if (right instanceof MutablePrimitive) {
            final MutablePrimitive rightMutablePrimitive = (MutablePrimitive) right;
            finalRightObject = rightMutablePrimitive.get();
        } else {
            finalRightObject = right;
        }

        return new ImmutablePair<>(arrayWrapperFunction.apply(finalLeftObject), arrayWrapperFunction.apply(finalRightObject));
    }
}
