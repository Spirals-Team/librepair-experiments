/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.runtime.rest.handler.legacy.metrics;

import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Request handler that returns for a given task a list of all available metrics or the values for a set of metrics.
 *
 * <p>If the query parameters do not contain a "get" parameter the list of all metrics is returned.
 * {@code {"available": [ { "name" : "X", "id" : "X" } ] } }
 *
 * <p>If the query parameters do contain a "get" parameter, a comma-separated list of metric names is expected as a value.
 * {@code /metrics?get=X,Y}
 * The handler will then return a list containing the values of the requested metrics.
 * {@code [ { "id" : "X", "value" : "S" }, { "id" : "Y", "value" : "T" } ] }
 *
 * @deprecated This class is subsumed by {@link SubtaskMetricsHandler} and is only kept for backwards-compatibility.
 */
@Deprecated
public class JobVertexMetricsHandler extends AbstractMetricsHandler {
	public static final String PARAMETER_VERTEX_ID = "vertexid";
	public static final String SEPARATE_FLAG = "@@";
	public static final String METRICS_SEPARATE_FLAG = ",";
	public static final String METRIC_NAME_SEPARATE_FLAG = ".";

	private static final String JOB_VERTEX_METRICS_REST_PATH = "/jobs/:jobid/vertices/:vertexid/metrics";

	public JobVertexMetricsHandler(Executor executor, MetricFetcher fetcher) {
		super(executor, fetcher);
	}

	@Override
	public String[] getPaths() {
		return new String[]{JOB_VERTEX_METRICS_REST_PATH};
	}

	@Override
	protected Map<String, String> getMapFor(Map<String, String> pathParams, MetricStore metrics) {
		MetricStore.ComponentMetricStore task = metrics.getTaskMetricStore(
			pathParams.get(JobMetricsHandler.PARAMETER_JOB_ID),
			pathParams.get(PARAMETER_VERTEX_ID));
		return task != null
			? task.metrics
			: null;
	}

	@Override
	protected String getRequestMetricsList(Map<String, String> queryParams) {
		String metricRequestsList = queryParams.get(PARAMETER_METRICS);
		if (metricRequestsList.contains(SEPARATE_FLAG)) {
			String[] metricRequestArray = metricRequestsList.split(SEPARATE_FLAG);
			int parallelism = Integer.valueOf(metricRequestArray[0]);
			String metricName = metricRequestArray[1];

			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < parallelism; i++) {
				sb.append(i);
				sb.append(METRIC_NAME_SEPARATE_FLAG);
				sb.append(metricName);
				sb.append(METRICS_SEPARATE_FLAG);
			}

			return sb.toString();
		} else {
			return queryParams.get(PARAMETER_METRICS);
		}

	}

}
