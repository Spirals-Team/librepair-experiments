/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.storm.util;

import org.apache.storm.spout.SpoutOutputCollector;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Tests for the SpoutOutputCollectorObserver.
 */
public class SpoutOutputCollectorObserverTest {

	@Test
	public void testFlag() {
		SpoutOutputCollectorObserver observer = new SpoutOutputCollectorObserver(mock(SpoutOutputCollector.class));

		observer.emitted = false;
		observer.emit(null);
		Assert.assertTrue(observer.emitted);

		observer.emitted = false;
		observer.emit(null, (Object) null);
		Assert.assertTrue(observer.emitted);

		observer.emitted = false;
		observer.emit((String) null, null);
		Assert.assertTrue(observer.emitted);

		observer.emitted = false;
		observer.emit(null, null, null);
		Assert.assertTrue(observer.emitted);

		observer.emitted = false;
		observer.emitDirect(0, null);
		Assert.assertTrue(observer.emitted);

		observer.emitted = false;
		observer.emitDirect(0, null, (Object) null);
		Assert.assertTrue(observer.emitted);

		observer.emitted = false;
		observer.emitDirect(0, (String) null, null);
		Assert.assertTrue(observer.emitted);

		observer.emitted = false;
		observer.emitDirect(0, null, null, null);
		Assert.assertTrue(observer.emitted);
	}

}
