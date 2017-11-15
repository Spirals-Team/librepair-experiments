/*
 * Copyright 2017 the original author or authors.
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

package org.springframework.cloud.dataflow.core;

/**
 * Spring Cloud Data Flow property keys.
 *
 * @author Ilayaperumal Gopinathan
 */
public class DataFlowPropertyKeys {

	public static final String PREFIX = "spring.cloud.dataflow.";

	/**
	 * Data Flow Stream property key prefix.
	 */
	private static final String STREAM_PREFIX = PREFIX + "stream.";

	/**
	 * Stream name property key.
	 */
	public static final String STREAM_NAME = STREAM_PREFIX + "name";

	/**
	 * Data Flow Stream app key prefix.
	 */
	private static final String STREAM_APP_PREFIX = STREAM_PREFIX + "app.";

	/**
	 * Stream app label property key.
	 */
	public static final String STREAM_APP_LABEL = STREAM_APP_PREFIX + "label";

	/**
	 * Stream app type property key.
	 */
	public static final String STREAM_APP_TYPE = STREAM_APP_PREFIX + "type";

}
