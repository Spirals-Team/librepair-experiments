package net.mirwaldt.jcomparison.core.value.epsilon;

import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.api.PairFactory;
import net.mirwaldt.jcomparison.core.pair.impl.*;
import net.mirwaldt.jcomparison.core.primitive.api.MutablePrimitive;
import net.mirwaldt.jcomparison.core.primitive.impl.*;
import net.mirwaldt.jcomparison.core.util.deduplicator.impl.NoDeduplicator;

import java.math.BigDecimal;
import java.math.BigInteger;

public class InputPairFactory implements PairFactory {
    public static final NoDeduplicator NO_DEDUPLICATOR = new NoDeduplicator();

    @Override
    public Pair<?> createPair(Object left, Object right) throws Exception {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Pair<?> createPair(Object left, Object right, Class<?> type) throws Exception {
        if(left instanceof Number && right instanceof Number) {
            final Number leftNumber = (Number) left;
            final Number rightNumber = (Number) right;
            
            if(MutablePrimitive.class.isAssignableFrom(type)) {
                if(type.equals(MutableByte.class)) {
                    final MutableByte leftMutableByte = new MutableByte(NO_DEDUPLICATOR);
                    leftMutableByte.setValue(leftNumber.byteValue());
                    final MutableByte rightMutableByte = new MutableByte(NO_DEDUPLICATOR);
                    rightMutableByte.setValue(rightNumber.byteValue());
                    
                    return new ImmutablePair<>(leftMutableByte, rightMutableByte);
                } else if(type.equals(MutableShort.class)) {
                    final MutableShort leftMutableShort = new MutableShort(NO_DEDUPLICATOR);
                    leftMutableShort.setValue(leftNumber.shortValue());
                    final MutableShort rightMutableShort = new MutableShort(NO_DEDUPLICATOR);
                    rightMutableShort.setValue(rightNumber.shortValue());

                    return new ImmutablePair<>(leftMutableShort, rightMutableShort);
                } else if(type.equals(MutableInt.class)) {
                    final MutableInt leftMutableInt = new MutableInt(NO_DEDUPLICATOR);
                    leftMutableInt.setValue(leftNumber.intValue());
                    final MutableInt rightMutableInt = new MutableInt(NO_DEDUPLICATOR);
                    rightMutableInt.setValue(rightNumber.intValue());

                    return new ImmutablePair<>(leftMutableInt, rightMutableInt);
                } else if(type.equals(MutableLong.class)) {
                    final MutableLong leftMutableLong = new MutableLong(NO_DEDUPLICATOR);
                    leftMutableLong.setValue(leftNumber.longValue());
                    final MutableLong rightMutableLong = new MutableLong(NO_DEDUPLICATOR);
                    rightMutableLong.setValue(rightNumber.longValue());

                    return new ImmutablePair<>(leftMutableLong, rightMutableLong);
                } else if(type.equals(MutableFloat.class)) {
                    final MutableFloat leftMutableFloat = new MutableFloat(NO_DEDUPLICATOR);
                    leftMutableFloat.setValue(leftNumber.floatValue());
                    final MutableFloat rightMutableFloat = new MutableFloat(NO_DEDUPLICATOR);
                    rightMutableFloat.setValue(rightNumber.floatValue());

                    return new ImmutablePair<>(leftMutableFloat, rightMutableFloat);
                } else if(type.equals(MutableDouble.class)) {
                    final MutableDouble leftMutableDouble = new MutableDouble(NO_DEDUPLICATOR);
                    leftMutableDouble.setValue(leftNumber.doubleValue());
                    final MutableDouble rightMutableDouble = new MutableDouble(NO_DEDUPLICATOR);
                    rightMutableDouble.setValue(rightNumber.doubleValue());

                    return new ImmutablePair<>(leftMutableDouble, rightMutableDouble);
                } else {
                    throw new IllegalArgumentException("Type '" +  type.getSimpleName() + "'  is not supported!");
                }
            } else if(type.equals(byte.class)) {
                return new ImmutableBytePair(leftNumber.byteValue(), rightNumber.byteValue());
            } else if(type.equals(short.class)) {
                return new ImmutableShortPair(leftNumber.shortValue(), rightNumber.shortValue());
            } else if(type.equals(int.class)) {
                return new ImmutableIntPair(leftNumber.intValue(), rightNumber.intValue());
            } else if(type.equals(long.class)) {
                return new ImmutableLongPair(leftNumber.longValue(), rightNumber.longValue());
            } else if(type.equals(float.class)) {
                return new ImmutableFloatPair(leftNumber.floatValue(), rightNumber.floatValue());
            } else if(type.equals(double.class)) {
                return new ImmutableDoublePair(leftNumber.doubleValue(), rightNumber.doubleValue());
            } else if(type.equals(BigInteger.class)) {
                return new ImmutablePair<>(
                        new BigInteger(String.valueOf(leftNumber.longValue())),
                        new BigInteger(String.valueOf(rightNumber.longValue())));
            } else if(type.equals(BigDecimal.class)) {
                return new ImmutablePair<>(
                        new BigDecimal(String.valueOf(leftNumber.doubleValue())).setScale(12, BigDecimal.ROUND_HALF_UP),
                        new BigDecimal(String.valueOf(rightNumber.doubleValue())).setScale(12, BigDecimal.ROUND_HALF_UP));
            } else {
                throw new IllegalArgumentException("Type '" +  type.getSimpleName() + "'  is not supported!");
            }
        } else {
            throw new IllegalArgumentException("At least one parameter is no number.'" 
                    + "\nleftValue='" + left + "', leftType='" + left.getClass() + "'"
                    + "\nrightValue='" + right + "', rightType='" + right.getClass() + "'");
        }
    }
}
