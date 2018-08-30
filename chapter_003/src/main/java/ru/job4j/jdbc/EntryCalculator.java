package ru.job4j.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry calculator.
 */
public class EntryCalculator {

    private static final Logger LOG = LoggerFactory.getLogger(EntryCalculator.class);

    /**
     * Calculate sum of entries
     *
     * @return sum of entries
     */
    public long aggregateSum(final Entries entries) {
        long sum = 0;
        for (Entry entry : entries.getEntry()) {
            sum += entry.getField();
        }
        LOG.info("Aggregated sum is: {}", sum);
        return sum;
    }
}
