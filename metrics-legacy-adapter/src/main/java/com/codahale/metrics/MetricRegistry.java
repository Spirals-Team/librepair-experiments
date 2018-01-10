package com.codahale.metrics;

import io.dropwizard.metrics5.MetricName;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

@Deprecated
public class MetricRegistry implements MetricSet {

    private final io.dropwizard.metrics5.MetricRegistry reg;

    public static String name(Class<?> klass, String... names) {
        return io.dropwizard.metrics5.MetricRegistry.name(klass, names).getKey();
    }

    public static String name(String name, String... names) {
        return io.dropwizard.metrics5.MetricRegistry.name(name, names).getKey();
    }

    public static MetricRegistry of(io.dropwizard.metrics5.MetricRegistry reg) {
        return new MetricRegistry(reg);
    }

    private MetricRegistry(io.dropwizard.metrics5.MetricRegistry reg) {
        this.reg = reg;
    }

    public <T extends Metric> T register(String name, T metric) throws IllegalArgumentException {
        reg.register(MetricName.build(name), metric.getDelegate());
        return metric;
    }

    public void registerAll(MetricSet metrics) throws IllegalArgumentException {
        reg.registerAll(metrics.transform());
    }

    public Counter counter(String name) {
        return new Counter(reg.counter(name));
    }

    public Histogram histogram(String name) {
        return new Histogram(reg.histogram(MetricName.build(name)));
    }

    public Meter meter(String name) {
        return new Meter(reg.meter(MetricName.build(name)));
    }

    public Timer timer(String name) {
        return new Timer(reg.timer(MetricName.build(name)));
    }

    public boolean remove(String name) {
        return reg.remove(MetricName.build(name));
    }

    public void removeMatching(MetricFilter filter) {
        reg.removeMatching(filter.transform());
    }

    public SortedSet<String> getNames() {
        final SortedSet<String> names = new TreeSet<>();
        for (MetricName name : reg.getNames()) {
            names.add(name.getKey());
        }
        return Collections.unmodifiableSortedSet(names);
    }

    public SortedMap<String, Gauge> getGauges() {
        return adaptMetrics(reg.getGauges());
    }

    public SortedMap<String, Gauge> getGauges(MetricFilter filter) {
        return adaptMetrics(reg.getGauges(filter.transform()));
    }

    public SortedMap<String, Counter> getCounters() {
        return adaptMetrics(reg.getHistograms());
    }

    public SortedMap<String, Counter> getCounters(MetricFilter filter) {
        return adaptMetrics(reg.getCounters(filter.transform()));
    }

    public SortedMap<String, Histogram> getHistograms() {
        return adaptMetrics(reg.getHistograms());
    }

    public SortedMap<String, Histogram> getHistograms(MetricFilter filter) {
        return adaptMetrics(reg.getHistograms(filter.transform()));
    }

    public SortedMap<String, Meter> getMeters() {
        return adaptMetrics(reg.getMeters());
    }

    public SortedMap<String, Meter> getMeters(MetricFilter filter) {
        return adaptMetrics(reg.getMeters(filter.transform()));
    }

    public SortedMap<String, Timer> getTimers() {
        return adaptMetrics(reg.getTimers());
    }

    public SortedMap<String, Timer> getTimers(MetricFilter filter) {
        return adaptMetrics(reg.getTimers(filter.transform()));
    }

    @Override
    public Map<String, Metric> getMetrics() {
        return adaptMetrics(reg.getMeters());
    }

    @SuppressWarnings("unchecked")
    private <T extends Metric> SortedMap<String, T> adaptMetrics(
            Map<MetricName, ? extends io.dropwizard.metrics5.Metric> metrics) {
        final SortedMap<String, T> items = new TreeMap<>();
        for (Map.Entry<MetricName, ? extends io.dropwizard.metrics5.Metric> entry : metrics.entrySet()) {
            items.put(entry.getKey().getKey(), (T) Metric.of(entry.getValue()));
        }
        return Collections.unmodifiableSortedMap(items);
    }
}
