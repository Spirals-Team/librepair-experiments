package net.mirwaldt.jcomparison.core.util.deduplicator.impl;

import net.mirwaldt.jcomparison.core.util.deduplicator.api.Deduplicator;

public class SynchronizedDeduplicator implements Deduplicator {
    private final Deduplicator deduplicator;

    public SynchronizedDeduplicator(Deduplicator deduplicator) {
        this.deduplicator = deduplicator;
    }

    @Override
    public synchronized Object deduplicate(Object value) {
        return deduplicator.deduplicate(value);
    }

    @Override
    public synchronized void clean() {
        deduplicator.clean();
    }
}
