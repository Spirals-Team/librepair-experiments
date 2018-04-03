package net.mirwaldt.jcomparison.core.value.epsilon;

import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.primitive.impl.*;
import net.mirwaldt.jcomparison.core.util.deduplicator.impl.NoDeduplicator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class ZeroHandlingRelativeEpsilonComparatorTest {
    
    static NoDeduplicator noDeduplicator = new NoDeduplicator();
    
    @ParameterizedTest
    @MethodSource("atLeastOneZero")
    void test_atLeastOneZero(Object leftObject, Object rightObject) throws ComparisonFailedException {
        final ValueComparator<Object> epsilonComparator = mock(ValueComparator.class);
        final ValueComparator<Object> equalsComparator = mock(ValueComparator.class);
        
        final ZeroHandlingRelativeEpsilonComparator zeroHandlingRelativeEpsilonComparator 
                = new ZeroHandlingRelativeEpsilonComparator(epsilonComparator, equalsComparator);
        
        zeroHandlingRelativeEpsilonComparator.compare(leftObject, rightObject);
        
        try {
            verify(equalsComparator).compare(eq(leftObject), eq(rightObject), any(VisitedObjectsTrace.class), any(ComparatorOptions.class));    
        } catch (AssertionError e) {
            throw e;
        }
        
        verifyNoMoreInteractions(epsilonComparator, equalsComparator);
    }

    @ParameterizedTest
    @MethodSource("noZero")
    void test_noZero(Object leftObject, Object rightObject) throws ComparisonFailedException {
        final ValueComparator<Object> epsilonComparator = mock(ValueComparator.class);
        final ValueComparator<Object> equalsComparator = mock(ValueComparator.class);

        final ZeroHandlingRelativeEpsilonComparator zeroHandlingRelativeEpsilonComparator
                = new ZeroHandlingRelativeEpsilonComparator(epsilonComparator, equalsComparator);

        zeroHandlingRelativeEpsilonComparator.compare(leftObject, rightObject);

        verify(epsilonComparator).compare(eq(leftObject), eq(rightObject), any(VisitedObjectsTrace.class), any(ComparatorOptions.class));
        verifyNoMoreInteractions(epsilonComparator, equalsComparator);
    }
    
    private static Stream<Arguments> atLeastOneZero() {
        return Stream.of(
                Arguments.of((byte)0, (byte)0),
                Arguments.of((short)0, (short)0),
                Arguments.of(0, 0),
                Arguments.of(0L, 0L),
                Arguments.of(0f, 0f),
                Arguments.of(0d, 0d),

                Arguments.of((byte)0, (byte)2),
                Arguments.of((short)0, (short)2),
                Arguments.of(0, 2),
                Arguments.of(0L, 2L),
                Arguments.of(0f, 2.0f),
                Arguments.of(0d, 2.0d),

                Arguments.of((byte)1, (byte)0),
                Arguments.of((short)1, (short)0),
                Arguments.of(1, 0),
                Arguments.of(1L, 0L),
                Arguments.of(1.0f, 0f),
                Arguments.of(1.0d, 0d),

                Arguments.of(new MutableByte((byte) 0, noDeduplicator), new MutableByte((byte) 0, noDeduplicator)),
                Arguments.of(new MutableShort((short) 0, noDeduplicator), new MutableShort((short) 0, noDeduplicator)),
                Arguments.of(new MutableInt(0, noDeduplicator), new MutableInt(0, noDeduplicator)),
                Arguments.of(new MutableLong(0L, noDeduplicator), new MutableLong(0L, noDeduplicator)),
                Arguments.of(new MutableFloat(0f, noDeduplicator), new MutableFloat(0f, noDeduplicator)),
                Arguments.of(new MutableDouble(0d, noDeduplicator), new MutableDouble(0d, noDeduplicator)),

                Arguments.of(new MutableByte((byte) 0, noDeduplicator), new MutableByte((byte) 2, noDeduplicator)),
                Arguments.of(new MutableShort((short) 0, noDeduplicator), new MutableShort((short) 2, noDeduplicator)),
                Arguments.of(new MutableInt(0, noDeduplicator), new MutableInt(2, noDeduplicator)),
                Arguments.of(new MutableLong(0L, noDeduplicator), new MutableLong(2L, noDeduplicator)),
                Arguments.of(new MutableFloat(0f, noDeduplicator), new MutableFloat(2.0f, noDeduplicator)),
                Arguments.of(new MutableDouble(0d, noDeduplicator), new MutableDouble(2.0d, noDeduplicator)),

                Arguments.of(new MutableByte((byte) 1, noDeduplicator), new MutableByte((byte) 0, noDeduplicator)),
                Arguments.of(new MutableShort((short) 1, noDeduplicator), new MutableShort((short) 0, noDeduplicator)),
                Arguments.of(new MutableInt(1, noDeduplicator), new MutableInt(0, noDeduplicator)),
                Arguments.of(new MutableLong(1L, noDeduplicator), new MutableLong(0L, noDeduplicator)),
                Arguments.of(new MutableFloat(1.0f, noDeduplicator), new MutableFloat(0f, noDeduplicator)),
                Arguments.of(new MutableDouble(1.0d, noDeduplicator), new MutableDouble(0d, noDeduplicator)),
               
                Arguments.of(BigInteger.ZERO, BigInteger.ZERO),
                Arguments.of(BigDecimal.ZERO, BigDecimal.ZERO),
                Arguments.of(BigInteger.valueOf(0), BigInteger.valueOf(0)),
                Arguments.of(BigDecimal.valueOf(0d), BigDecimal.valueOf(0d)),

                Arguments.of(BigInteger.ZERO, BigInteger.valueOf(2L)),
                Arguments.of(BigDecimal.ZERO, BigDecimal.valueOf(2.0d)),
                Arguments.of(BigInteger.valueOf(0), BigInteger.valueOf(2)),
                Arguments.of(BigDecimal.valueOf(0d), BigDecimal.valueOf(2.0d)),

                Arguments.of(BigInteger.valueOf(1L), BigInteger.ZERO),
                Arguments.of(BigDecimal.valueOf(1.0d), BigDecimal.ZERO),
                Arguments.of(BigInteger.valueOf(1L), BigInteger.valueOf(0)),
                Arguments.of(BigDecimal.valueOf(1.0d), BigDecimal.valueOf(0d))
        );
    }

    private static Stream<Arguments> noZero() {
        return Stream.of(
                Arguments.of((byte)1, (byte)2),
                Arguments.of((short)1, (short)2),
                Arguments.of(1, 2),
                Arguments.of(1L, 2L),
                Arguments.of(1.0f, 2.0f),
                Arguments.of(1.0d, 2.0d),
                Arguments.of(new MutableByte((byte) 1, noDeduplicator), new MutableByte((byte) 2, noDeduplicator)),
                Arguments.of(new MutableShort((short) 1, noDeduplicator), new MutableShort((short) 2, noDeduplicator)),
                Arguments.of(new MutableInt(1, noDeduplicator), new MutableInt(2, noDeduplicator)),
                Arguments.of(new MutableLong(1L, noDeduplicator), new MutableLong(2L, noDeduplicator)),
                Arguments.of(new MutableFloat(1.0f, noDeduplicator), new MutableFloat(2.0f, noDeduplicator)),
                Arguments.of(new MutableDouble(1.0d, noDeduplicator), new MutableDouble(2.0d, noDeduplicator)),
                Arguments.of(BigInteger.valueOf(1L), BigInteger.valueOf(2L)),
                Arguments.of(BigDecimal.valueOf(1.0d), BigDecimal.valueOf(2.0d))
        );
    }
}
