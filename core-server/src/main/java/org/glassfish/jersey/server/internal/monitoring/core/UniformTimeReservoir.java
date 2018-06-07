/*
 * Copyright (c) 2015, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2010, 2013 Coda Hale and Yammer, Inc.
 *
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

package org.glassfish.jersey.server.internal.monitoring.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

/**
 * A random sampling reservoir of a stream of {@code long}s. Uses Vitter's Algorithm R to produce a statistically representative
 * sample.
 *
 * @author Stepan Vavra (stepan.vavra at oracle.com)
 * @author Dropwizard Team
 * @see <a href="http://www.cs.umd.edu/~samir/498/vitter.pdf">Random Sampling with a Reservoir</a>
 * @see <a href="https://github.com/dropwizard/metrics">https://github.com/dropwizard/metrics</a>
 */
public class UniformTimeReservoir implements TimeReservoir<Long> {

    private final long startTime;
    private final TimeUnit startTimeUnit;

    private static final int DEFAULT_SIZE = 1024;
    private static final int BITS_PER_LONG = 63;
    private final AtomicLong count = new AtomicLong();
    private final AtomicLongArray values;

    /**
     * Creates a new {@code UniformTimeReservoir} instance of 1024 elements, which offers a 99.9% confidence level
     * with a 5% margin of error assuming a normal distribution.
     *
     * @param startTime     The start time
     * @param startTimeUnit The start time unit
     */
    public UniformTimeReservoir(final long startTime, final TimeUnit startTimeUnit) {
        this(DEFAULT_SIZE, startTime, startTimeUnit);
    }

    /**
     * Creates a new {@code UniformTimeReservoir} instance.
     *
     * @param size          the number of samples to keep in the sampling reservoir
     * @param startTime     The start time
     * @param startTimeUnit The start time unit
     */
    public UniformTimeReservoir(int size, final long startTime, final TimeUnit startTimeUnit) {
        this.startTime = startTime;
        this.startTimeUnit = startTimeUnit;
        this.values = new AtomicLongArray(size);
        for (int i = 0; i < values.length(); i++) {
            values.set(i, 0);
        }
        count.set(0);
    }

    @Override
    public int size(final long time, final TimeUnit timeUnit) {
        final long c = count.get();
        if (c > values.length()) {
            return values.length();
        }
        return (int) c;
    }

    @Override
    public void update(Long value, final long time, final TimeUnit timeUnit) {
        final long c = count.incrementAndGet();
        if (c <= values.length()) {
            values.set((int) c - 1, value);
        } else {
            final long r = nextLong(c);
            if (r < values.length()) {
                values.set((int) r, value);
            }
        }
    }

    /**
     * Get a pseudo-random long uniformly between 0 and n-1. Stolen from {@link java.util.Random#nextInt()}.
     *
     * @param n the bound
     * @return a value select randomly from the range {@code [0..n)}.
     */
    private static long nextLong(long n) {
        long bits;
        long val;
        do {
            bits = ThreadLocalRandom.current().nextLong() & (~(1L << BITS_PER_LONG));
            val = bits % n;
        } while (bits - val + (n - 1) < 0L);
        return val;
    }

    @Override
    public UniformTimeSnapshot getSnapshot(final long time, final TimeUnit timeUnit) {
        final int s = size(time, timeUnit);
        final List<Long> copy = new ArrayList<>(s);
        for (int i = 0; i < s; i++) {
            copy.add(values.get(i));
        }
        return new UniformTimeValuesSnapshot(copy,
                startTimeUnit.convert(time, timeUnit) - startTime,
                startTimeUnit) {

            /**
             * Method size must be overridden because it returns the values size by default which is {@link
             * UniformTimeReservoir#DEFAULT_SIZE}.
             *
             * @return total number of data entries in the reservoir.
             */
            @Override
            public long size() {
                return count.get();
            }
        };
    }

    @Override
    public long interval(final TimeUnit timeUnit) {
        // Uniform Interval returns 0 for infinity
        return 0;
    }
}
