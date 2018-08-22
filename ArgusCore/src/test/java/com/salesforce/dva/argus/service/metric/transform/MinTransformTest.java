/*
 * Copyright (c) 2016, Salesforce.com, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of Salesforce.com nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
     
package com.salesforce.dva.argus.service.metric.transform;

import com.salesforce.dva.argus.entity.Metric;
import org.junit.Ignore;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MinTransformTest {

    private static final String TEST_SCOPE = "test-scope";
    private static final String TEST_METRIC = "test-metric";

    @Test(expected = UnsupportedOperationException.class)
    public void testMinTransformWithConstants() {
        Transform minTransform = new MetricReducerTransform(new MinValueReducer());
        List<Metric> metrics = new ArrayList<Metric>();
        List<String> constants = new ArrayList<String>(1);

        constants.add("5");
        minTransform.transform(metrics, constants);
    }

    @Test(expected = IllegalArgumentException.class)
    @Ignore
    public void testMinTransformWithoutMetrics() {
        Transform minTransform = new MetricReducerTransform(new MinValueReducer());
        List<Metric> metrics = new ArrayList<Metric>();

        minTransform.transform(metrics);
    }

    @Test
    public void testMinTransformWithCommonDPs() {
        Transform minTransform = new MetricReducerTransform(new MinValueReducer());
        Map<Long, Double> datapoints_1 = new HashMap<Long, Double>();

        datapoints_1.put(1000L, 1.0);
        datapoints_1.put(2000L, 2.0);
        datapoints_1.put(3000L, 3.0);

        Metric metric_1 = new Metric(TEST_SCOPE, TEST_METRIC);

        metric_1.setDatapoints(datapoints_1);

        Map<Long, Double> datapoints_2 = new HashMap<Long, Double>();

        datapoints_2.put(1000L, 10.0);
        datapoints_2.put(2000L, 100.0);
        datapoints_2.put(3000L, 1000.0);

        Metric metric_2 = new Metric(TEST_SCOPE, TEST_METRIC);

        metric_2.setDatapoints(datapoints_2);

        List<Metric> metrics = new ArrayList<Metric>();

        metrics.add(metric_1);
        metrics.add(metric_2);

        Map<Long, Double> expected = new HashMap<Long, Double>();

        expected.put(1000L, 1.0);
        expected.put(2000L, 2.0);
        expected.put(3000L, 3.0);

        List<Metric> result = minTransform.transform(metrics);

        assertEquals(result.get(0).getDatapoints().size(), 3);
        assertEquals(expected, result.get(0).getDatapoints());
    }

    @Test
    public void testMinTransformWithNoCommonDPs() {
        Transform minTransform = new MetricReducerTransform(new MinValueReducer());
        Map<Long, Double> datapoints_1 = new HashMap<Long, Double>();

        datapoints_1.put(1000L, 1.0);
        datapoints_1.put(2000L, 2.0);
        datapoints_1.put(3000L, 3.0);

        Metric metric_1 = new Metric(TEST_SCOPE, TEST_METRIC);

        metric_1.setDatapoints(datapoints_1);

        Map<Long, Double> datapoints_2 = new HashMap<Long, Double>();

        datapoints_2.put(100L, 10.0);
        datapoints_2.put(200L, 100.0);
        datapoints_2.put(300L, 1000.0);

        Metric metric_2 = new Metric(TEST_SCOPE, TEST_METRIC);

        metric_2.setDatapoints(datapoints_2);

        List<Metric> metrics = new ArrayList<Metric>();

        metrics.add(metric_1);
        metrics.add(metric_2);

        Map<Long, Double> expected = new HashMap<Long, Double>();

        expected.putAll(datapoints_1);
        expected.putAll(datapoints_2);

        List<Metric> result = minTransform.transform(metrics);

        assertEquals(6, result.get(0).getDatapoints().size());
        assertEquals(expected, result.get(0).getDatapoints());
    }

    @Test
    public void testMinTransformWithSomeCommonDPs() {
        Transform minTransform = new MetricReducerTransform(new MinValueReducer());
        Map<Long, Double> datapoints_1 = new HashMap<Long, Double>();

        datapoints_1.put(1000L, 1.0);
        datapoints_1.put(2000L, 2.0);
        datapoints_1.put(3000L, 3.0);

        Metric metric_1 = new Metric(TEST_SCOPE, TEST_METRIC);

        metric_1.setDatapoints(datapoints_1);

        Map<Long, Double> datapoints_2 = new HashMap<Long, Double>();

        datapoints_2.put(100L, 10.0);
        datapoints_2.put(200L, 100.0);
        datapoints_2.put(3000L, 1000.0);

        Metric metric_2 = new Metric(TEST_SCOPE, TEST_METRIC);

        metric_2.setDatapoints(datapoints_2);

        List<Metric> metrics = new ArrayList<Metric>();

        metrics.add(metric_1);
        metrics.add(metric_2);

        Map<Long, Double> expected = new HashMap<Long, Double>();

        expected.putAll(datapoints_1);
        expected.putAll(datapoints_2);
        expected.put(3000L, 3.0);

        List<Metric> result = minTransform.transform(metrics);

        assertEquals(5, result.get(0).getDatapoints().size());
        assertEquals(expected, result.get(0).getDatapoints());
    }

    @Test
    public void testMinTransformWithMissingDPs() {
        Transform minTransform = new MetricReducerTransform(new MinValueReducer());
        Map<Long, Double> datapoints_1 = new HashMap<Long, Double>();

        datapoints_1.put(1000L, 1.0);
        datapoints_1.put(2000L, 2.0);
        datapoints_1.put(3000L, 3.0);

        Metric metric_1 = new Metric(TEST_SCOPE, TEST_METRIC);

        metric_1.setDatapoints(datapoints_1);

        Map<Long, Double> datapoints_2 = new HashMap<Long, Double>();

        datapoints_2.put(100L, 10.0);
        datapoints_2.put(200L, 100.0);
        datapoints_2.put(3000L, null);

        Metric metric_2 = new Metric(TEST_SCOPE, TEST_METRIC);

        metric_2.setDatapoints(datapoints_2);

        List<Metric> metrics = new ArrayList<Metric>();

        metrics.add(metric_1);
        metrics.add(metric_2);

        Map<Long, Double> expected = new HashMap<Long, Double>();

        expected.putAll(datapoints_1);
        expected.putAll(datapoints_2);
        expected.put(3000L, 3.0);

        List<Metric> result = minTransform.transform(metrics);

        assertEquals(5, result.get(0).getDatapoints().size());
        assertEquals(expected, result.get(0).getDatapoints());
    }

    @Test
    public void testMinTransformWithAllMissingDPsAtTimestamp() {
        Transform minTransform = new MetricReducerTransform(new MinValueReducer());
        Map<Long, Double> datapoints_1 = new HashMap<Long, Double>();

        datapoints_1.put(1000L, 1.0);
        datapoints_1.put(2000L, 2.0);
        datapoints_1.put(3000L, null);

        Metric metric_1 = new Metric(TEST_SCOPE, TEST_METRIC);

        metric_1.setDatapoints(datapoints_1);

        Map<Long, Double> datapoints_2 = new HashMap<Long, Double>();

        datapoints_2.put(1000L, 10.0);
        datapoints_2.put(2000L, 100.0);
        datapoints_2.put(3000L, null);

        Metric metric_2 = new Metric(TEST_SCOPE, TEST_METRIC);

        metric_2.setDatapoints(datapoints_2);

        List<Metric> metrics = new ArrayList<Metric>();

        metrics.add(metric_1);
        metrics.add(metric_2);

        Map<Long, Double> expected = new HashMap<Long, Double>();

        expected.put(1000L, 1.0);
        expected.put(2000L, 2.0);
        expected.put(3000L, null);

        List<Metric> result = minTransform.transform(metrics);

        assertEquals(result.get(0).getDatapoints().size(), 3);
        assertEquals(expected, result.get(0).getDatapoints());
    }
    
    @Test
    public void testMinTransformScopeNameWhenScopeIsSameAcrossMetrics() {
    	Transform minTransform = new MetricReducerTransform(new MinValueReducer());
    	Map<Long, Double> datapoints_1 = new HashMap<Long, Double>();

        datapoints_1.put(1000L, 1.0);
        datapoints_1.put(2000L, 2.0);
        datapoints_1.put(3000L, 3.0);

        Metric metric_1 = new Metric(TEST_SCOPE, TEST_METRIC);

        metric_1.setDatapoints(datapoints_1);

        Map<Long, Double> datapoints_2 = new HashMap<Long, Double>();

        datapoints_2.put(100L, 10.0);
        datapoints_2.put(200L, 100.0);
        datapoints_2.put(3000L, 1000.0);

        Metric metric_2 = new Metric(TEST_SCOPE, TEST_METRIC);

        metric_2.setDatapoints(datapoints_2);

        List<Metric> metrics = Arrays.asList(metric_1, metric_2);
        Metric result = minTransform.transform(metrics).get(0);
        
        assertEquals(metric_1.getScope(), result.getScope());
    }
    
    @Test
    public void testMinTransformScopeNameWhenScopeIsDifferentAcrossMetrics() {
    	Transform minTransform = new MetricReducerTransform(new MinValueReducer());
    	Map<Long, Double> datapoints_1 = new HashMap<Long, Double>();

        datapoints_1.put(1000L, 1.0);
        datapoints_1.put(2000L, 2.0);
        datapoints_1.put(3000L, 3.0);

        Metric metric_1 = new Metric("scope", "metric");

        metric_1.setDatapoints(datapoints_1);

        Map<Long, Double> datapoints_2 = new HashMap<Long, Double>();

        datapoints_2.put(100L, 10.0);
        datapoints_2.put(200L, 100.0);
        datapoints_2.put(3000L, 1000.0);

        Metric metric_2 = new Metric("scope1", "metric");

        metric_2.setDatapoints(datapoints_2);

        List<Metric> metrics = Arrays.asList(metric_1, metric_2);
        Metric result = minTransform.transform(metrics).get(0);
        
        assertEquals(new MinValueReducer().name(), result.getScope());
    }
}
/* Copyright (c) 2016, Salesforce.com, Inc.  All rights reserved. */
