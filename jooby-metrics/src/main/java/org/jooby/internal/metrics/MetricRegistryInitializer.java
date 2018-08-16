/**
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
package org.jooby.internal.metrics;

import java.io.Closeable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Reporter;

import javaslang.control.Try;

public class MetricRegistryInitializer {

  private Set<Closeable> reporters = new HashSet<>();

  /** The logging system. */
  private final Logger log = LoggerFactory.getLogger(MetricRegistry.class);

  @Inject
  public MetricRegistryInitializer(final MetricRegistry registry, final Map<String, Metric> metrics,
      final Set<Reporter> reporters) {
    metrics.forEach(registry::register);
    reporters.forEach(reporter -> {
      if (reporter instanceof Closeable) {
        this.reporters.add((Closeable) reporter);
      }
    });
  }

  public void close() throws Exception {
    reporters.forEach(r -> Try.run(() -> r.close())
        .onFailure(cause -> log.error("close of {} resulted in error", r, cause)));
  }

}
