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

public interface RelativeEpsilonDoubleComparatorTestable {
    double epsilonInPercentSmallerThan1 = 0.4d;
    double epsilonInPercentBiggerThan1 = 2d;
    PairFactory inputPairFactory = new InputPairFactory();
    PairFactory outputPairFactory = ValueComparators.pairFactory;
    
    DoubleFunction<Supplier<ValueComparator<Object>>> getValueComparatorSupplier();
    
    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_insideEpsilon_bothSameNegative(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(-1.25d, -1.25d, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilonInPercentSmallerThan1).get().compare(inputPair.getLeft(), inputPair.getRight());

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
        final Pair<?> inputPair = inputPairFactory.createPair(2.78d, 2.78d, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilonInPercentSmallerThan1).get().compare(inputPair.getLeft(), inputPair.getRight());

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
        final Pair<?> inputPair = inputPairFactory.createPair(10.08d, 13.12d, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilonInPercentSmallerThan1).get().compare(inputPair.getLeft(), inputPair.getRight());

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
        final Pair<?> inputPair = inputPairFactory.createPair(10.0d, 14.0d, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilonInPercentSmallerThan1).get().compare(inputPair.getLeft(), inputPair.getRight());

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
        final Pair<?> inputPair = inputPairFactory.createPair(1.76d, 7.32d, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilonInPercentSmallerThan1).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertFalse(valueComparisonResult.hasSimilarities());


        assertTrue(valueComparisonResult.hasDifferences());

        final Pair<Object> valueDifference = valueComparisonResult.getDifferences();
        assertEquals(outputPair, valueDifference);


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_insideEpsilon_onePositiveAndOneNegative(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(-1d, 1d, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());
        
        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilonInPercentBiggerThan1).get().compare(inputPair.getLeft(), inputPair.getRight());

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
        final Pair<?> inputPair = inputPairFactory.createPair(-2d, 1d, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilonInPercentBiggerThan1).get().compare(inputPair.getLeft(), inputPair.getRight());

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
        final Pair<?> inputPair = inputPairFactory.createPair(1.01d, -9.47d, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilonInPercentBiggerThan1).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertFalse(valueComparisonResult.hasSimilarities());


        assertTrue(valueComparisonResult.hasDifferences());

        final Pair<Object> valueDifference = valueComparisonResult.getDifferences();
        assertEquals(outputPair, valueDifference);


        assertFalse(valueComparisonResult.hasComparisonResults());
    }

    @ParameterizedTest
    @MethodSource("getTypeStream")
    default void test_insideEpsilon_bothNegative(Class<?> testedType) throws Exception {
        final Pair<?> inputPair = inputPairFactory.createPair(-31.289d, -45.18d, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilonInPercentSmallerThan1).get().compare(inputPair.getLeft(), inputPair.getRight());

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
        final Pair<?> inputPair = inputPairFactory.createPair(-0.1d, -0.14d, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilonInPercentSmallerThan1).get().compare(inputPair.getLeft(), inputPair.getRight());

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
        final Pair<?> inputPair = inputPairFactory.createPair(-100.5091d, -Math.PI, testedType);
        final Pair<?> outputPair = outputPairFactory.createPair(inputPair.getLeft(), inputPair.getRight());

        final ValueComparisonResult<Object> valueComparisonResult =
                getValueComparatorSupplier().apply(epsilonInPercentSmallerThan1).get().compare(inputPair.getLeft(), inputPair.getRight());

        assertFalse(valueComparisonResult.hasSimilarities());


        assertTrue(valueComparisonResult.hasDifferences());

        final Pair<Object> valueDifference = valueComparisonResult.getDifferences();
        assertEquals(outputPair, valueDifference);


        assertFalse(valueComparisonResult.hasComparisonResults());
    }
}
