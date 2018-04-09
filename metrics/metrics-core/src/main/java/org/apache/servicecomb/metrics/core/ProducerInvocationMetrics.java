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

package org.apache.servicecomb.metrics.core;

import java.util.concurrent.TimeUnit;

import org.apache.servicecomb.foundation.metrics.MetricsConst;

public class ProducerInvocationMetrics extends AbstractInvocationMetrics {
  public ProducerInvocationMetrics(String... tags) {
    super(tags);
    this.addLatencyMonitors(MetricsConst.STAGE_QUEUE, tags);
    this.addLatencyMonitors(MetricsConst.STAGE_EXECUTION, tags);
  }

  public void update(long inQueueNanoTime, long executionElapsedNanoTime, long totalElapsedNanoTime) {
    this.updateCallMonitors();
    this.updateLatencyMonitors(MetricsConst.STAGE_QUEUE, inQueueNanoTime, TimeUnit.NANOSECONDS);
    this.updateLatencyMonitors(MetricsConst.STAGE_EXECUTION, executionElapsedNanoTime, TimeUnit.NANOSECONDS);
    this.updateLatencyMonitors(MetricsConst.STAGE_TOTAL, totalElapsedNanoTime, TimeUnit.NANOSECONDS);
  }
}
