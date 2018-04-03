package net.mirwaldt.jcomparison.core.util.deduplicator.impl;

import net.mirwaldt.jcomparison.core.util.deduplicator.api.Deduplicator;

import java.util.function.Predicate;

public class FilterDeduplicator implements Deduplicator {
    private final Predicate<Object> filter;
    private final Deduplicator deduplicator;

    public FilterDeduplicator(Predicate<Object> filter, Deduplicator deduplicator) {
        this.filter = filter;
        this.deduplicator = deduplicator;
    }

    @Override
    public Object deduplicate(Object value) {
        if (filter.test(value)) {
            return deduplicator.deduplicate(value);
        } else {
            return value;
        }
    }

    @Override
    public void clean() {
        deduplicator.clean();
    }
}