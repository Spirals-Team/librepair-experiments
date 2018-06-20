/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nifi.controller.status.history;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.nifi.controller.status.ProcessorStatus;
import org.apache.nifi.controller.status.history.MetricDescriptor.Formatter;

public enum ProcessorStatusDescriptor {
    BYTES_READ(new StandardMetricDescriptor<ProcessorStatus>(
        "bytesRead",
        "Bytes Read (5 mins)",
        "The total number of bytes read from the Content Repository by this Processor in the past 5 minutes",
        Formatter.DATA_SIZE,
        s -> s.getBytesRead())),

    BYTES_WRITTEN(new StandardMetricDescriptor<ProcessorStatus>(
        "bytesWritten",
        "Bytes Written (5 mins)",
        "The total number of bytes written to the Content Repository by this Processor in the past 5 minutes",
        Formatter.DATA_SIZE,
        s -> s.getBytesWritten())),

    BYTES_TRANSFERRED(new StandardMetricDescriptor<ProcessorStatus>(
        "bytesTransferred",
        "Bytes Transferred (5 mins)",
        "The total number of bytes read from or written to the Content Repository by this Processor in the past 5 minutes",
        Formatter.DATA_SIZE,
        s -> s.getBytesRead() + s.getBytesWritten())),

    INPUT_BYTES(new StandardMetricDescriptor<ProcessorStatus>(
        "inputBytes",
        "Bytes In (5 mins)",
        "The cumulative size of all FlowFiles that this Processor has pulled from its queues in the past 5 minutes",
        Formatter.DATA_SIZE,
        s -> s.getInputBytes())),

    INPUT_COUNT(new StandardMetricDescriptor<ProcessorStatus>(
        "inputCount",
        "FlowFiles In (5 mins)",
        "The number of FlowFiles that this Processor has pulled from its queues in the past 5 minutes",
        Formatter.COUNT,
        s -> Long.valueOf(s.getInputCount()))),

    OUTPUT_BYTES(new StandardMetricDescriptor<ProcessorStatus>(
        "outputBytes",
        "Bytes Out (5 mins)",
        "The cumulative size of all FlowFiles that this Processor has transferred to downstream queues in the past 5 minutes",
        Formatter.DATA_SIZE,
        s -> s.getOutputBytes())),

    OUTPUT_COUNT(new StandardMetricDescriptor<ProcessorStatus>(
        "outputCount",
        "FlowFiles Out (5 mins)",
        "The number of FlowFiles that this Processor has transferred to downstream queues in the past 5 minutes",
        Formatter.COUNT,
        s -> Long.valueOf(s.getOutputCount()))),

    TASK_COUNT(new StandardMetricDescriptor<ProcessorStatus>(
        "taskCount",
        "Tasks (5 mins)",
        "The number of tasks that this Processor has completed in the past 5 minutes",
        Formatter.COUNT,
        s -> Long.valueOf(s.getInvocations()))),

    TASK_MILLIS(new StandardMetricDescriptor<ProcessorStatus>(
        "taskMillis",
        "Total Task Duration (5 mins)",
        "The total number of thread-milliseconds that the Processor has used to complete its tasks in the past 5 minutes",
        Formatter.DURATION,
        s -> TimeUnit.MILLISECONDS.convert(s.getProcessingNanos(), TimeUnit.NANOSECONDS))),

    TASK_NANOS(new StandardMetricDescriptor<ProcessorStatus>(
        "taskNanos",
        "Total Task Time (nanos)",
        "The total number of thread-nanoseconds that the Processor has used to complete its tasks in the past 5 minutes",
        Formatter.COUNT,
        ProcessorStatus::getProcessingNanos), false),

    FLOWFILES_REMOVED(new StandardMetricDescriptor<ProcessorStatus>(
        "flowFilesRemoved",
        "FlowFiles Removed (5 mins)",
        "The total number of FlowFiles removed by this Processor in the last 5 minutes",
        Formatter.COUNT,
        s -> Long.valueOf(s.getFlowFilesRemoved()))),

    AVERAGE_LINEAGE_DURATION(new StandardMetricDescriptor<ProcessorStatus>(
        "averageLineageDuration",
        "Average Lineage Duration (5 mins)",
        "The average amount of time that a FlowFile took to process (from receipt until this Processor finished processing it) in the past 5 minutes.",
        Formatter.DURATION,
        s -> s.getAverageLineageDuration(TimeUnit.MILLISECONDS),
        new ValueReducer<StatusSnapshot, Long>() {
            @Override
            public Long reduce(final List<StatusSnapshot> values) {
                long millis = 0L;
                int count = 0;

                for (final StatusSnapshot snapshot : values) {
                    final long removed = snapshot.getStatusMetrics().get(FLOWFILES_REMOVED.getDescriptor()).longValue();
                    final long outputCount = snapshot.getStatusMetrics().get(OUTPUT_COUNT.getDescriptor()).longValue();
                    final long processed = removed + outputCount;

                    count += processed;

                    final long avgMillis = snapshot.getStatusMetrics().get(AVERAGE_LINEAGE_DURATION.getDescriptor()).longValue();
                    final long totalMillis = avgMillis * processed;
                    millis += totalMillis;
                }

                return count == 0 ? 0 : millis / count;
            }
        }
    )),

    AVERAGE_TASK_NANOS(new StandardMetricDescriptor<ProcessorStatus>(
        "averageTaskNanos",
        "Average Task Duration (nanoseconds)",
        "The average number of nanoseconds it took this Processor to complete a task, over the past 5 minutes",
        Formatter.COUNT,
        s -> s.getInvocations() == 0 ? 0 : s.getProcessingNanos() / s.getInvocations(),
        new ValueReducer<StatusSnapshot, Long>() {
            @Override
            public Long reduce(final List<StatusSnapshot> values) {
                long procNanos = 0L;
                int invocations = 0;

                for (final StatusSnapshot snapshot : values) {
                    final Long taskNanos = snapshot.getStatusMetrics().get(TASK_NANOS.getDescriptor());
                    if (taskNanos != null) {
                        procNanos += taskNanos.longValue();
                    }

                    final Long taskInvocations = snapshot.getStatusMetrics().get(TASK_COUNT.getDescriptor());
                    if (taskInvocations != null) {
                        invocations += taskInvocations.intValue();
                    }
                }

                if (invocations == 0) {
                    return 0L;
                }

                return procNanos / invocations;
            }
        }));



    private final MetricDescriptor<ProcessorStatus> descriptor;
    private final boolean visible;

    private ProcessorStatusDescriptor(final MetricDescriptor<ProcessorStatus> descriptor) {
        this(descriptor, true);
    }

    private ProcessorStatusDescriptor(final MetricDescriptor<ProcessorStatus> descriptor, final boolean visible) {
        this.descriptor = descriptor;
        this.visible = visible;
    }

    public String getField() {
        return descriptor.getField();
    }

    public MetricDescriptor<ProcessorStatus> getDescriptor() {
        return descriptor;
    }

    public boolean isVisible() {
        return visible;
    }
}
