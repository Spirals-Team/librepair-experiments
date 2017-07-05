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

package org.apache.flink.runtime.zookeeper;

import java.io.Closeable;
import java.io.Serializable;

/**
 * Service interface for the RetrievableStateStorageHelper which allows to close and clean up its data.
 *
 * @param <T> The type of the data that can be stored by this storage service.
 */
public interface RetrievableStateStorageService<T extends Serializable>
	extends RetrievableStateStorageHelper<T>, Closeable {
	/**
	 * Closes and cleans up the store. This entails the deletion of all states.
	 * @throws Exception
	 */
	void closeAndCleanupAllData() throws Exception;
}
