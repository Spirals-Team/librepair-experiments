/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.apiman.gateway.engine;

import io.apiman.gateway.engine.metrics.RequestMetric;

/**
 * An interface used by the engine to report metric information as it
 * processes requests.  Each request is reported to the metrics system
 * so that it can be recorded/aggregated/analyzed.  
 *
 * @author eric.wittmann@redhat.com
 */
public interface IMetrics {

    /**
     * Records the metrics for a single request.  Most implementations will likely
     * asynchronously process this information.
     * @param metric the request metric
     */
    public void record(RequestMetric metric);
    
    /**
     * Provides the component registry (before any call to {@link #record(RequestMetric)})
     * is made. Metrics can then access HTTP client components, etc.
     * @param registry the component registry
     */
    public void setComponentRegistry(IComponentRegistry registry); 
}
