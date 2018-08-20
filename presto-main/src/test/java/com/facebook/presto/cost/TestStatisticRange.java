/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.cost;

import org.testng.annotations.Test;

import static com.facebook.presto.cost.EstimateAssertion.assertEstimateEquals;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;
import static org.testng.Assert.assertEquals;

public class TestStatisticRange
{
    @Test
    public void testOverlapPercentWith()
    {
        StatisticRange zeroToTen = range(0, 10, 10);
        StatisticRange empty = StatisticRange.empty();

        // Equal ranges
        assertOverlap(zeroToTen, range(0, 10, 5), 1);
        assertOverlap(zeroToTen, range(0, 10, 20), 1);
        assertOverlap(zeroToTen, range(0, 10, 20), 1);

        // Some overlap
        assertOverlap(zeroToTen, range(5, 3000, 3), 0.5);

        // Single value overlap
        assertOverlap(zeroToTen, range(3, 3, 1), 1 / zeroToTen.getDistinctValuesCount());
        assertOverlap(zeroToTen, range(10, 100, 357), 1 / zeroToTen.getDistinctValuesCount());

        // No overlap
        assertOverlap(zeroToTen, range(20, 30, 10), 0);

        // Empty ranges
        assertOverlap(zeroToTen, empty, 0);
        assertOverlap(empty, zeroToTen, 0);
        // no test for empty, empty) since any return value is correct
        assertOverlap(unboundedRange(10), empty, 0);

        // Unbounded (infinite), NDV-based
        assertOverlap(unboundedRange(10), unboundedRange(20), 1);
        assertOverlap(unboundedRange(20), unboundedRange(10), 0.5);
    }

    @Test
    public void testIntersect()
    {
        StatisticRange zeroToTen = range(0, 10, 10);
        StatisticRange fiveToFifteen = range(5, 15, 60);
        assertEquals(zeroToTen.intersect(fiveToFifteen), range(5, 10, 10));
    }

    private static StatisticRange range(double low, double high, double distinctValues)
    {
        return new StatisticRange(low, high, distinctValues);
    }

    private static StatisticRange unboundedRange(double distinctValues)
    {
        return new StatisticRange(NEGATIVE_INFINITY, POSITIVE_INFINITY, distinctValues);
    }

    private static void assertOverlap(StatisticRange a, StatisticRange b, double expected)
    {
        assertEstimateEquals(a.overlapPercentWith(b), expected, "overlapPercentWith");
    }
}
