package net.mirwaldt.jcomparison.core.iterable.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutablePair;
import net.mirwaldt.jcomparison.core.decorator.tracing.ExceptionAtCycleComparator;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparatorProvider;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.iterable.api.EachWithEachComparisonResult;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class BasicEachWithEachComparatorTest {

    final DefaultComparatorProvider comparatorProvider = new DefaultComparatorProvider(
            true, false, Function.identity(),
            new ExceptionAtCycleComparator(), (builder, internalComparatorProvider) -> {
    }
    );

    final DefaultEachWithEachComparator defaultEachWithEachComparator =
            DefaultComparators.createDefaultEachWithEachComparatorBuilder().useComparatorProvider(comparatorProvider).build();

    @Test
    public void test_eachWithEachComparison() throws ComparisonFailedException {
        final List<List<Integer>> leftListOfIntLists = Arrays.asList(Arrays.asList(1), Arrays.asList(2, 3, 4));
        final List<List<Integer>> righListOfIntLists = Arrays.asList(Arrays.asList(1, 2), Arrays.asList(2, 3));

        final EachWithEachComparisonResult eachWithEachComparisonResult =
                defaultEachWithEachComparator.compare(leftListOfIntLists, righListOfIntLists);

        assertFalse(eachWithEachComparisonResult.hasSimilarity());

        assertFalse(eachWithEachComparisonResult.hasDifference());

        assertTrue(eachWithEachComparisonResult.hasComparisonResults());

        final Map<Pair<Object>, ComparisonResult<?, ?, ?>> comparisonResultMap = eachWithEachComparisonResult.getComparisonResults();
        assertEquals(4, comparisonResultMap.size());

        assertNotNull(comparisonResultMap.get(new ImmutablePair<Object>(leftListOfIntLists.get(0), righListOfIntLists.get(0))));
        assertNotNull(comparisonResultMap.get(new ImmutablePair<Object>(leftListOfIntLists.get(1), righListOfIntLists.get(0))));
        assertNotNull(comparisonResultMap.get(new ImmutablePair<Object>(leftListOfIntLists.get(0), righListOfIntLists.get(1))));
        assertNotNull(comparisonResultMap.get(new ImmutablePair<Object>(leftListOfIntLists.get(1), righListOfIntLists.get(1))));
    }

}
