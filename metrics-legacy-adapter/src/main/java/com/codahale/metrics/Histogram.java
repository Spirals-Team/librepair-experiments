package com.codahale.metrics;

import io.dropwizard.metrics5.Reservoir;

@Deprecated
public class Histogram implements Metric, Sampling, Counting {

    private final io.dropwizard.metrics5.Histogram delegate;

    public Histogram(io.dropwizard.metrics5.Histogram delegate) {
        this.delegate = delegate;
    }

    public Histogram(Reservoir reservoir) {
        this.delegate = new io.dropwizard.metrics5.Histogram(reservoir);
    }

    public void update(int value) {
        delegate.update(value);
    }

    public void update(long value) {
        delegate.update(value);
    }

    @Override
    public long getCount() {
        return delegate.getCount();
    }

    @Override
    public Snapshot getSnapshot() {
        return new Snapshot.Adapter(delegate.getSnapshot());
    }

    @Override
    public io.dropwizard.metrics5.Metric getDelegate() {
        return delegate;
    }
}
