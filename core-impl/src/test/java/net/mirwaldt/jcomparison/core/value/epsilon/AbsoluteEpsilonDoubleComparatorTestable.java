package net.mirwaldt.jcomparison.core.value.epsilon;

import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.api.PairFactory;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.DoubleFunction;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

public interface AbsoluteEpsilonDoubleComparatorTestable {
    double epsilon = 2.5d;
    PairFactory inputPairFactory = new InputPairFactory();
    PairFactory outputPairFactory = ValueComparators.pairFactory;

    DoubleFunction<Supplier<ValueComparator<Object>>> getValueComparatorSupplier();

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_insideEpsilon_bothZero(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(0d, 0d, testedType);
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
    default void test_insideEpsilon_bothSameNegative(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(-1d, -1d, testedType);
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
    default void test_insideEpsilon_bothSamePositive(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(2d, 2d, testedType);
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
    default void test_insideEpsilon_bothPositive(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(10.34d, 12.1d, testedType);
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
        final Pair<?> inputPair = inputPairFactory.createPair(0.6d, 3.1d, testedType);
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
        final Pair<?> inputPair = inputPairFactory.createPair(1.3d, 5.666d, testedType);
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
        final Pair<?> inputPair = inputPairFactory.createPair(-0.312, 1.9687, testedType);
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
        final Pair<?> inputPair = inputPairFactory.createPair(-2.06d, 0.44d, testedType);
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
        final Pair<?> inputPair = inputPairFactory.createPair(1.0d, -4.0, testedType);
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
        final Pair<?> inputPair = inputPairFactory.createPair(-3.1d, -2.27d, testedType);
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
        final Pair<?> inputPair = inputPairFactory.createPair(-3.87d, -6.37d, testedType);
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
        final Pair<?> inputPair = inputPairFactory.createPair(-10.65354d, -3.12534d, testedType);
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
        final Pair<?> inputPair = inputPairFactory.createPair(0d, 1.91d, testedType);
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
        final Pair<?> inputPair = inputPairFactory.createPair(2.5d, 0d, testedType);
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
        final Pair<?> inputPair = inputPairFactory.createPair(0d, 9.8d, testedType);
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
        final Pair<?> inputPair = inputPairFactory.createPair(-2.14d, 0d, testedType);
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
        final Pair<?> inputPair = inputPairFactory.createPair(0d, -2.5d, testedType);
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
        final Pair<?> inputPair = inputPairFactory.createPair(-10.63d, 0d, testedType);
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
