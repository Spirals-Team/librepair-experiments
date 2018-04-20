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
package com.facebook.presto.resourceGroups;

import com.facebook.presto.spi.session.ResourceEstimates;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.airlift.json.JsonCodec;
import io.airlift.units.DataSize;
import io.airlift.units.Duration;

import java.util.Objects;
import java.util.Optional;

import static io.airlift.json.JsonCodec.jsonCodec;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public final class ResourceEstimatesRule
{
    private final Optional<ResourceEstimateRange<Duration>> executionTimeRange;
    private final Optional<ResourceEstimateRange<Duration>> cpuTimeRange;
    private final Optional<ResourceEstimateRange<DataSize>> peakMemoryRange;

    @JsonCreator
    public ResourceEstimatesRule(
            @JsonProperty("executionTimeRange") Optional<ResourceEstimateRange<Duration>> executionTimeRange,
            @JsonProperty("cpuTimeRange") Optional<ResourceEstimateRange<Duration>> cpuTimeRange,
            @JsonProperty("peakMemoryRange") Optional<ResourceEstimateRange<DataSize>> peakMemoryRange)
    {
        this.executionTimeRange = requireNonNull(executionTimeRange, "executionTimeRange is null");
        this.cpuTimeRange = requireNonNull(cpuTimeRange, "cpuTimeRange is null");
        this.peakMemoryRange = requireNonNull(peakMemoryRange, "peakMemoryRange is null");
    }

    @JsonProperty
    public Optional<ResourceEstimateRange<Duration>> getExecutionTimeRange()
    {
        return executionTimeRange;
    }

    @JsonProperty
    public Optional<ResourceEstimateRange<Duration>> getCpuTimeRange()
    {
        return cpuTimeRange;
    }

    @JsonProperty
    public Optional<ResourceEstimateRange<DataSize>> getPeakMemoryRange()
    {
        return peakMemoryRange;
    }

    boolean match(ResourceEstimates resourceEstimates)
    {
        if (executionTimeRange.isPresent() &&
                !(resourceEstimates.getExecutionTime().isPresent() && executionTimeRange.get().checkWithinRange(resourceEstimates.getExecutionTime().get()))) {
            return false;
        }

        if (cpuTimeRange.isPresent() &&
                !(resourceEstimates.getCpuTime().isPresent() && cpuTimeRange.get().checkWithinRange(resourceEstimates.getCpuTime().get()))) {
            return false;
        }

        if (peakMemoryRange.isPresent() &&
                !(resourceEstimates.getPeakMemory().isPresent() && peakMemoryRange.get().checkWithinRange(resourceEstimates.getPeakMemory().get()))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("ResourceEstimatesRule{");
        sb.append("executionTimeRange=").append(executionTimeRange);
        sb.append(", cpuTimeRange=").append(cpuTimeRange);
        sb.append(", peakMemoryRange=").append(peakMemoryRange);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(executionTimeRange, cpuTimeRange, peakMemoryRange);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResourceEstimatesRule other = (ResourceEstimatesRule) o;

        return Objects.equals(this.executionTimeRange, other.executionTimeRange) &&
                Objects.equals(this.cpuTimeRange, other.cpuTimeRange) &&
                Objects.equals(this.peakMemoryRange, other.peakMemoryRange);
    }

    public static class ResourceEstimateRange<T extends Comparable<T>>
    {
        private final Optional<T> min;
        private final Optional<T> max;

        @JsonCreator
        public ResourceEstimateRange(
                @JsonProperty("min") Optional<T> min,
                @JsonProperty("max") Optional<T> max)
        {
            this.min = requireNonNull(min, "min is null");
            this.max = requireNonNull(max, "max is null");
        }

        public boolean checkWithinRange(T value)
        {
            return (!min.isPresent() || min.get().compareTo(value) <= 0) &&
                    (!max.isPresent() || max.get().compareTo(value) >= 0);
        }

        @JsonProperty
        public Optional<T> getMin()
        {
            return min;
        }

        @JsonProperty
        public Optional<T> getMax()
        {
            return max;
        }

        @Override
        public String toString()
        {
            return format("ResourceEstimateRange{min=%s, max=%s}", min, max);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(min, max);
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ResourceEstimateRange<?> other = (ResourceEstimateRange<?>) o;

            return Objects.equals(this.min, other.min) && Objects.equals(this.max, other.max);
        }
    }

    public static void main(String[] args)
    {
        ResourceEstimatesRule x = new ResourceEstimatesRule(
                Optional.of(new ResourceEstimateRange<>(Optional.of(Duration.valueOf("1h")), Optional.empty())),
                Optional.empty(),
                Optional.empty());

        JsonCodec<ResourceEstimatesRule> jsonCodec = jsonCodec(ResourceEstimatesRule.class);
        System.out.println(jsonCodec.toJson(x));

        String xx = jsonCodec.toJson(x);
        ResourceEstimatesRule rule = jsonCodec.fromJson(xx);
        System.out.println(rule);
    }
}
