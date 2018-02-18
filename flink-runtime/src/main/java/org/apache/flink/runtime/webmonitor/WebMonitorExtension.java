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

package org.apache.flink.runtime.webmonitor;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.runtime.rest.handler.RestHandlerSpecification;
import org.apache.flink.util.CloseableAsync;

import org.apache.flink.shaded.netty4.io.netty.channel.ChannelInboundHandler;

import java.util.Collection;

/**
 * Interface for {@link WebMonitorEndpoint} extensions. Extensions can register
 * additional handler and allow to close their resources in order to integrate
 * into the life cycle of the {@link WebMonitorEndpoint}.
 */
public interface WebMonitorExtension extends CloseableAsync {

	/**
	 * Gets the collection of extension handlers to register at the {@link WebMonitorEndpoint}.
	 *
	 * @return Collection of handlers to register at the {@link WebMonitorEndpoint}.
	 */
	Collection<Tuple2<RestHandlerSpecification, ChannelInboundHandler>> getHandlers();
}
