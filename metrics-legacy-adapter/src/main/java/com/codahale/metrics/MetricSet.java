package com.codahale.metrics;

import io.dropwizard.metrics5.MetricName;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public interface MetricSet {

    Map<String, Metric> getMetrics();

    default io.dropwizard.metrics5.MetricSet transform() {
        final MetricSet original = this;
        return () -> {
            final Map<MetricName, io.dropwizard.metrics5.Metric> items = new HashMap<>();
            for (Map.Entry<String, Metric> entry : original.getMetrics().entrySet()) {
                items.put(MetricName.build(entry.getKey()), entry.getValue().getDelegate());
            }
            return Collections.unmodifiableMap(items);
        };
    }

}
