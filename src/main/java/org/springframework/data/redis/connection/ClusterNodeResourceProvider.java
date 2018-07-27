/*
 * Copyright 2015 the original author or authors.
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
package org.springframework.data.redis.connection;

/**
 * {@link ClusterNodeResourceProvider} provides access to low level client api to directly execute operations against a
 * Redis instance.
 * 
 * @author Christoph Strobl
 * @since 1.7
 */
public interface ClusterNodeResourceProvider {

	/**
	 * Get the client resource for the given node.
	 * 
	 * @param node must not be {@literal null}.
	 * @return
	 */
	<S> S getResourceForSpecificNode(RedisClusterNode node);

	/**
	 * Return the resource object for the given node. This can mean free up resources or return elements back to a pool.
	 * 
	 * @param node must not be {@literal null}.
	 * @param resource must not be {@literal null}.
	 */
	void returnResourceForSpecificNode(RedisClusterNode node, Object resource);

}
