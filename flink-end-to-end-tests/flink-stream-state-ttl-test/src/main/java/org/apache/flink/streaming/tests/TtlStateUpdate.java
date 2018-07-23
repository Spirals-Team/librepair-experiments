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

package org.apache.flink.streaming.tests;

import javax.annotation.Nonnull;

import java.io.Serializable;
import java.util.Map;

/** Randomly generated keyed state updates per state type. */
class TtlStateUpdate implements Serializable {
	private final int key;

	@Nonnull
	private final Map<String, Object> updates;

	TtlStateUpdate(int key, @Nonnull Map<String, Object> updates) {
		this.key = key;
		this.updates = updates;
	}

	int getKey() {
		return key;
	}

	Object getUpdate(String verifierId) {
		return updates.get(verifierId);
	}
}
