package net.mirwaldt.jcomparison.core.string.impl;

import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.util.view.impl.ImmutableIntRange;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvalidIntRangeSubstringComparatorTest {

    @Test
    public void test_twoEmptyStrings_leftRange_invalidStartIndex() throws ComparisonFailedException {
        final String leftValue = "";
        final String rightValue = "";

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().build();

        final ImmutableIntRange leftIntRange = new ImmutableIntRange(1, 1);
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(0, 0);

        assertThrows(ComparisonFailedException.class, () -> substringComparator.compare(leftValue, rightValue, leftIntRange, rightIntRange));
    }

    @Test
    public void test_twoEmptyStrings_rightRange_invalidStartIndex() throws ComparisonFailedException {
        final String leftValue = "";
        final String rightValue = "";

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().build();

        final ImmutableIntRange leftIntRange = new ImmutableIntRange(0, 0);
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(1, 2);

        assertThrows(ComparisonFailedException.class, () -> substringComparator.compare(leftValue, rightValue, leftIntRange, rightIntRange));
    }

    @Test
    public void test_twoEmptyStrings_bothRanges_invalidStartIndex() throws ComparisonFailedException {
        final String leftValue = "";
        final String rightValue = "";

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().build();

        final ImmutableIntRange leftIntRange = new ImmutableIntRange(1, 3);
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(2, 5);

        assertThrows(ComparisonFailedException.class, () -> substringComparator.compare(leftValue, rightValue, leftIntRange, rightIntRange));
    }
    
    @Test
    public void test_twoEmptyStrings_leftRange_invalidEndIndex() throws ComparisonFailedException {
        final String leftValue = "";
        final String rightValue = "";

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().build();

        final ImmutableIntRange leftIntRange = new ImmutableIntRange(0, 1);
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(0, 0);

        assertThrows(ComparisonFailedException.class, () -> substringComparator.compare(leftValue, rightValue, leftIntRange, rightIntRange));
    }
    
    @Test
    public void test_twoEmptyStrings_rightRange_invalidEndIndex() throws ComparisonFailedException {
        final String leftValue = "";
        final String rightValue = "";

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().build();

        final ImmutableIntRange leftIntRange = new ImmutableIntRange(0, 0);
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(0, 2);

        assertThrows(ComparisonFailedException.class, () -> substringComparator.compare(leftValue, rightValue, leftIntRange, rightIntRange));
    }
    
    @Test
    public void test_twoEmptyStrings_bothRanges_invalidEndIndex() throws ComparisonFailedException {
        final String leftValue = "";
        final String rightValue = "";

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().build();

        final ImmutableIntRange leftIntRange = new ImmutableIntRange(0, 3);
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(0, 5);

        assertThrows(ComparisonFailedException.class, () -> substringComparator.compare(leftValue, rightValue, leftIntRange, rightIntRange));
    }
    
    // ----------------------------- non empty Strings --------------------------------- 

    @Test
    public void test_twoNonEmptyStrings_leftRange_invalidStartIndex() throws ComparisonFailedException {
        final String leftValue = "a";
        final String rightValue = "bcd";

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().build();

        final ImmutableIntRange leftIntRange = new ImmutableIntRange(1, 1);
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(0, 0);

        assertThrows(ComparisonFailedException.class, () -> substringComparator.compare(leftValue, rightValue, leftIntRange, rightIntRange));
    }

    @Test
    public void test_twoNonEmptyStrings_rightRange_invalidStartIndex() throws ComparisonFailedException {
        final String leftValue = "a";
        final String rightValue = "bcd";

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().build();

        final ImmutableIntRange leftIntRange = new ImmutableIntRange(0, 0);
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(4, 5);

        assertThrows(ComparisonFailedException.class, () -> substringComparator.compare(leftValue, rightValue, leftIntRange, rightIntRange));
    }

    @Test
    public void test_twoNonEmptyStrings_bothRanges_invalidStartIndex() throws ComparisonFailedException {
        final String leftValue = "a";
        final String rightValue = "bcd";

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().build();

        final ImmutableIntRange leftIntRange = new ImmutableIntRange(1, 3);
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(5, 6);

        assertThrows(ComparisonFailedException.class, () -> substringComparator.compare(leftValue, rightValue, leftIntRange, rightIntRange));
    }

    @Test
    public void test_twoNonEmptyStrings_leftRange_invalidEndIndex() throws ComparisonFailedException {
        final String leftValue = "a";
        final String rightValue = "bcd";

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().build();

        final ImmutableIntRange leftIntRange = new ImmutableIntRange(0, 3);
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(0, 0);

        assertThrows(ComparisonFailedException.class, () -> substringComparator.compare(leftValue, rightValue, leftIntRange, rightIntRange));
    }

    @Test
    public void test_twoNonEmptyStrings_rightRange_invalidEndIndex() throws ComparisonFailedException {
        final String leftValue = "a";
        final String rightValue = "bcd";

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().build();

        final ImmutableIntRange leftIntRange = new ImmutableIntRange(0, 0);
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(0, 7);

        assertThrows(ComparisonFailedException.class, () -> substringComparator.compare(leftValue, rightValue, leftIntRange, rightIntRange));
    }

    @Test
    public void test_twoNonEmptyStrings_bothRanges_invalidEndIndex() throws ComparisonFailedException {
        final String leftValue = "a";
        final String rightValue = "bcd";

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().build();

        final ImmutableIntRange leftIntRange = new ImmutableIntRange(0, 3);
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(0, 5);

        assertThrows(ComparisonFailedException.class, () -> substringComparator.compare(leftValue, rightValue, leftIntRange, rightIntRange));
    }
}
