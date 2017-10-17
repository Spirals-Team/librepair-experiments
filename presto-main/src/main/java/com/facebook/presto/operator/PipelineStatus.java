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
package com.facebook.presto.operator;

import javax.annotation.concurrent.Immutable;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Immutable
public class PipelineStatus
{
    private final int queuedDrivers;
    private final int runningDrivers;
    private final int blockedDrivers;
    private final int queuedPartitionedDrivers;
    private final int runningPartitionedDrivers;

    public PipelineStatus(List<DriverContext> driverContexts)
    {
        requireNonNull(driverContexts, "driverContexts is null");

        int queuedDrivers = 0;
        int runningDrivers = 0;
        int blockedDrivers = 0;
        int queuedPartitionedDrivers = 0;
        int runningPartitionedDrivers = 0;
        for (DriverContext driverContext : driverContexts) {
            if (!driverContext.executionStarted()) {
                queuedDrivers++;
                if (driverContext.isPartitioned()) {
                    queuedPartitionedDrivers++;
                }
            }
            else if (driverContext.isFullyBlocked()) {
                blockedDrivers++;
            }
            else {
                runningDrivers++;
                if (driverContext.isPartitioned()) {
                    runningPartitionedDrivers++;
                }
            }
        }

        this.queuedDrivers = queuedDrivers;
        this.runningDrivers = runningDrivers;
        this.blockedDrivers = blockedDrivers;
        this.queuedPartitionedDrivers = queuedPartitionedDrivers;
        this.runningPartitionedDrivers = runningPartitionedDrivers;
    }

    public int getQueuedDrivers()
    {
        return queuedDrivers;
    }

    public int getRunningDrivers()
    {
        return runningDrivers;
    }

    public int getBlockedDrivers()
    {
        return blockedDrivers;
    }

    public int getQueuedPartitionedDrivers()
    {
        return queuedPartitionedDrivers;
    }

    public int getRunningPartitionedDrivers()
    {
        return runningPartitionedDrivers;
    }
}
