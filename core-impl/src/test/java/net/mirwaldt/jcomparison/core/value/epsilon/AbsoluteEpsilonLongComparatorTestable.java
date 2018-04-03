package net.mirwaldt.jcomparison.core.value.epsilon;

import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.api.PairFactory;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.LongFunction;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

public interface AbsoluteEpsilonLongComparatorTestable {
    int epsilon = 3;
    PairFactory inputPairFactory = new InputPairFactory();
    PairFactory outputPairFactory = ValueComparators.pairFactory;
    
    LongFunction<Supplier<ValueComparator<Object>>> getValueComparatorSupplier();
    
    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_insideEpsilon_bothZero(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(0, 0, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());
        
        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertTrue(valueComparisonResult.hasSimilarities());

        final ValueSimilarity<Object> valueSimilarity = valueComparisonResult.getSimilarities();
        assertTrue(valueSimilarity.isOneSimilarValue());
        assertEquals(outputPair.getLeft(), valueSimilarity.getSimilarValue());


        assertFalse(valueComparisonResult.hasDifferences());


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_insideEpsilon_bothSameNegative(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(-1, -1, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertTrue(valueComparisonResult.hasSimilarities());

        final ValueSimilarity<Object> valueSimilarity = valueComparisonResult.getSimilarities();
        assertTrue(valueSimilarity.isOneSimilarValue());
        assertEquals(outputPair.getLeft(), valueSimilarity.getSimilarValue());


        assertFalse(valueComparisonResult.hasDifferences());


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_insideEpsilon_bothSamePositive(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(2, 2, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertTrue(valueComparisonResult.hasSimilarities());

        final ValueSimilarity<Object> valueSimilarity = valueComparisonResult.getSimilarities();
        assertTrue(valueSimilarity.isOneSimilarValue());
        assertEquals(outputPair.getLeft(), valueSimilarity.getSimilarValue());


        assertFalse(valueComparisonResult.hasDifferences());


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_insideEpsilon_bothPositive(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(10, 12, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertTrue(valueComparisonResult.hasSimilarities());

        final ValueSimilarity<Object> valueSimilarity = valueComparisonResult.getSimilarities();
        assertFalse(valueSimilarity.isOneSimilarValue());
        assertEquals(outputPair, valueSimilarity.getSimilarValues());


        assertFalse(valueComparisonResult.hasDifferences());


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_atEpsilon_bothPositive(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(0, 3, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertTrue(valueComparisonResult.hasSimilarities());

        final ValueSimilarity<Object> valueSimilarity = valueComparisonResult.getSimilarities();
        assertFalse(valueSimilarity.isOneSimilarValue());
        assertEquals(outputPair, valueSimilarity.getSimilarValues());


        assertFalse(valueComparisonResult.hasDifferences());


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_outsideEpsilon_bothPositive(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(1, 5, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertFalse(valueComparisonResult.hasSimilarities());


        assertTrue(valueComparisonResult.hasDifferences());

        final Pair<Object> valueDifference = valueComparisonResult.getDifferences();
        assertEquals(outputPair, valueDifference);


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_insideEpsilon_onePositiveAndOneNegative(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(-1, 1, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());
        
        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertTrue(valueComparisonResult.hasSimilarities());

        final ValueSimilarity<Object> valueSimilarity = valueComparisonResult.getSimilarities();
        assertFalse(valueSimilarity.isOneSimilarValue());
        assertEquals(outputPair, valueSimilarity.getSimilarValues());


        assertFalse(valueComparisonResult.hasDifferences());


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_atEpsilon_onePositiveAndOneNegative(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(-2, 1, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertTrue(valueComparisonResult.hasSimilarities());

        final ValueSimilarity<Object> valueSimilarity = valueComparisonResult.getSimilarities();
        assertFalse(valueSimilarity.isOneSimilarValue());
        assertEquals(outputPair, valueSimilarity.getSimilarValues());


        assertFalse(valueComparisonResult.hasDifferences());


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_outsideEpsilon_onePositiveAndOneNegative(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(1, -5, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertFalse(valueComparisonResult.hasSimilarities());


        assertTrue(valueComparisonResult.hasDifferences());

        final Pair<Object> valueDifference = valueComparisonResult.getDifferences();
        assertEquals(outputPair, valueDifference);


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_insideEpsilon_bothNegative(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(-3, -2, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertTrue(valueComparisonResult.hasSimilarities());

        final ValueSimilarity<Object> valueSimilarity = valueComparisonResult.getSimilarities();
        assertFalse(valueSimilarity.isOneSimilarValue());
        assertEquals(outputPair, valueSimilarity.getSimilarValues());


        assertFalse(valueComparisonResult.hasDifferences());


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_atEpsilon_bothNegative(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(-3, -6, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertTrue(valueComparisonResult.hasSimilarities());

        final ValueSimilarity<Object> valueSimilarity = valueComparisonResult.getSimilarities();
        assertFalse(valueSimilarity.isOneSimilarValue());
        assertEquals(outputPair, valueSimilarity.getSimilarValues());


        assertFalse(valueComparisonResult.hasDifferences());


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_outsideEpsilon_bothNegative(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(-10, -3, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertFalse(valueComparisonResult.hasSimilarities());


        assertTrue(valueComparisonResult.hasDifferences());

        final Pair<Object> valueDifference = valueComparisonResult.getDifferences();
        assertEquals(outputPair, valueDifference);


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_insideEpsilon_onePositiveAndOneZero(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(0, 1, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertTrue(valueComparisonResult.hasSimilarities());

        final ValueSimilarity<Object> valueSimilarity = valueComparisonResult.getSimilarities();
        assertFalse(valueSimilarity.isOneSimilarValue());
        assertEquals(outputPair, valueSimilarity.getSimilarValues());


        assertFalse(valueComparisonResult.hasDifferences());


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_atEpsilon_onePositiveAndOneZero(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(3, 0, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertTrue(valueComparisonResult.hasSimilarities());

        final ValueSimilarity<Object> valueSimilarity = valueComparisonResult.getSimilarities();
        assertFalse(valueSimilarity.isOneSimilarValue());
        assertEquals(outputPair, valueSimilarity.getSimilarValues());


        assertFalse(valueComparisonResult.hasDifferences());


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_outsideEpsilon_onePositiveAndOneZero(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(0, 9, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertFalse(valueComparisonResult.hasSimilarities());


        assertTrue(valueComparisonResult.hasDifferences());

        final Pair<Object> valueDifference = valueComparisonResult.getDifferences();
        assertEquals(outputPair, valueDifference);


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_insideEpsilon_oneNegativeAndOneZero(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(-2, 0, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertTrue(valueComparisonResult.hasSimilarities());

        final ValueSimilarity<Object> valueSimilarity = valueComparisonResult.getSimilarities();
        assertFalse(valueSimilarity.isOneSimilarValue());
        assertEquals(outputPair, valueSimilarity.getSimilarValues());


        assertFalse(valueComparisonResult.hasDifferences());


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_atEpsilon_oneNegativeAndOneZero(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(0, -3, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertTrue(valueComparisonResult.hasSimilarities());

        final ValueSimilarity<Object> valueSimilarity = valueComparisonResult.getSimilarities();
        assertFalse(valueSimilarity.isOneSimilarValue());
        assertEquals(outputPair, valueSimilarity.getSimilarValues());


        assertFalse(valueComparisonResult.hasDifferences());


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_outsideEpsilon_oneNegativeAndOneZero(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(-10, 0, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilon).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertFalse(valueComparisonResult.hasSimilarities());


        assertTrue(valueComparisonResult.hasDifferences());

        final Pair<Object> valueDifference = valueComparisonResult.getDifferences();
        assertEquals(outputPair, valueDifference);


        assertFalse(valueComparisonResult.hasComparisonResults());
    }
}
