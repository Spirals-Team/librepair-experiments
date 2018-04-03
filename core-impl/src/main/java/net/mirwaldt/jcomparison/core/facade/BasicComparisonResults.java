package net.mirwaldt.jcomparison.core.facade;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.api.ListComparisonResult;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;

public class BasicComparisonResults {
    
    @SuppressWarnings("rawtypes")
    private static final ComparisonResult EMPTY_NON_VALUE_COMPARISON_RESULT = new ComparisonResult() {
        @Override
        public String toString() {
            return "EMPTY_VALUE_COMPARISON_RESULT";
        }
    };

    @SuppressWarnings("unchecked")
    public static ComparisonResult<?, ?, ?> emptyNonValueComparisonResult() {
        return EMPTY_NON_VALUE_COMPARISON_RESULT;
    }

    @SuppressWarnings("rawtypes")
    private static ValueComparisonResult EMPTY_VALUE_COMPARISON_RESULT = new ValueComparisonResult() {
        @Override
        public String toString() {
            return "EMPTY_VALUE_COMPARISON_RESULT";
        }
    };

    @SuppressWarnings("unchecked")
    public static <ValueType2> ValueComparisonResult<ValueType2> emptyValueComparisonResult() {
        return EMPTY_VALUE_COMPARISON_RESULT;
    }
    
    @SuppressWarnings("rawtypes")
    private static final ComparisonResult SKIP_COMPARISON_RESULT = new ComparisonResult() {
        @Override
        public String toString() {
            return "SKIP_COMPARISON_RESULT";
        }
    };

    @SuppressWarnings("unchecked")
    public static <T, U, V> ComparisonResult<T, U, V> skipComparisonResult() {
        return (ComparisonResult<T, U, V>) SKIP_COMPARISON_RESULT;
    }

    @SuppressWarnings("rawtypes")
    private static final ComparisonResult STOP_COMPARISON_RESULT = new ComparisonResult() {
        @Override
        public String toString() {
            return "STOP_COMPARISON_RESULT";
        }
    };

    @SuppressWarnings("unchecked")
    public static <T, U, V> ComparisonResult<T, U, V> stopComparisonResult() {
        return (ComparisonResult<T, U, V>) STOP_COMPARISON_RESULT;
    }
    
    private static ArrayComparisonResult EMPTY_ARRAY_COMPARISON_RESULT = new ArrayComparisonResult() {
        @Override
        public String toString() {
            return "EMPTY_ARRAY_COMPARISON_RESULT";
        }
    };

    @SuppressWarnings("unchecked")
    public static ArrayComparisonResult emptyArrayComparisonResult() {
        return EMPTY_ARRAY_COMPARISON_RESULT;
    }
    
}
