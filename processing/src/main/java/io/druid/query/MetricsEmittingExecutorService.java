/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.query;

import com.google.common.util.concurrent.ForwardingListeningExecutorService;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import io.druid.java.util.emitter.service.ServiceEmitter;
import io.druid.java.util.emitter.service.ServiceMetricEvent;

import java.util.concurrent.Callable;

public class MetricsEmittingExecutorService extends ForwardingListeningExecutorService
    implements ExecutorServiceMonitor.MetricEmitter
{
  private final ListeningExecutorService delegate;

  public MetricsEmittingExecutorService(
      ListeningExecutorService delegate,
      ExecutorServiceMonitor executorServiceMonitor
  )
  {
    super();
    this.delegate = delegate;
    executorServiceMonitor.add(this);
  }

  @Override
  protected ListeningExecutorService delegate()
  {
    return delegate;
  }

  @SuppressWarnings("ParameterPackage")
  @Override
  public <T> ListenableFuture<T> submit(Callable<T> tCallable)
  {
    return delegate.submit(tCallable);
  }

  @Override
  public void execute(Runnable runnable)
  {
    delegate.execute(runnable);
  }

  @Override
  public void emitMetrics(ServiceEmitter emitter, ServiceMetricEvent.Builder metricBuilder)
  {
    if (delegate instanceof PrioritizedExecutorService) {
      emitter.emit(metricBuilder.build("segment/scan/pending", ((PrioritizedExecutorService) delegate).getQueueSize()));
    }
  }

}
