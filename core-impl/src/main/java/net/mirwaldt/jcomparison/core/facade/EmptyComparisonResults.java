package net.mirwaldt.jcomparison.core.facade;

import net.mirwaldt.jcomparison.core.collection.list.api.ListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.set.api.SetComparisonResult;
import net.mirwaldt.jcomparison.core.map.api.MapComparisonResult;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;

public class EmptyComparisonResults {
    private static ListComparisonResult EMPTY_LIST_COMPARISON_RESULT = new ListComparisonResult() {
        @Override
        public String toString() {
            return "EMPTY_LIST_COMPARISON_RESULT";
        }
    };

    @SuppressWarnings("unchecked")
    public static <T> ListComparisonResult<T> emptyListComparisonResult() {
        return (ListComparisonResult<T>) EMPTY_LIST_COMPARISON_RESULT;
    }

    private static SetComparisonResult EMPTY_SET_COMPARISON_RESULT = new SetComparisonResult() {
        @Override
        public String toString() {
            return "EMPTY_SET_COMPARISON_RESULT";
        }
    };

    @SuppressWarnings("unchecked")
    public static <T> SetComparisonResult<T> emptySetComparisonResult() {
        return (SetComparisonResult<T>) EMPTY_SET_COMPARISON_RESULT;
    }
    
    private static MapComparisonResult EMPTY_MAP_COMPARISON_RESULT = new MapComparisonResult() {
        @Override
        public String toString() {
            return "EMPTY_MAP_COMPARISON_RESULT";
        }
    };

    @SuppressWarnings("unchecked")
    public static <K,V> MapComparisonResult<K,V> emptyMapComparisonResult() {
        return (MapComparisonResult<K,V>) EMPTY_MAP_COMPARISON_RESULT;
    }

    private static SubstringComparisonResult EMPTY_SUBSTRING_COMPARISON_RESULT = new SubstringComparisonResult() {
        @Override
        public String toString() {
            return "EMPTY_SUBSTRING_COMPARISON_RESULT";
        }
    };

    @SuppressWarnings("unchecked")
    public static SubstringComparisonResult emptySubstringComparisonResult() {
        return EMPTY_SUBSTRING_COMPARISON_RESULT;
    }
}
