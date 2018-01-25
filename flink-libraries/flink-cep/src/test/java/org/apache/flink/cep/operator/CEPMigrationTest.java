/*
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

package org.apache.flink.cep.operator;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeutils.base.ByteSerializer;
import org.apache.flink.api.common.typeutils.base.IntSerializer;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.cep.Event;
import org.apache.flink.cep.SubEvent;
import org.apache.flink.cep.nfa.NFA;
import org.apache.flink.cep.nfa.compiler.NFACompiler;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.apache.flink.streaming.util.KeyedOneInputStreamOperatorTestHarness;
import org.apache.flink.streaming.util.OneInputStreamOperatorTestHarness;
import org.junit.Test;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CEPMigrationTest {

	private static String getResourceFilename(String filename) {
		ClassLoader cl = CEPMigrationTest.class.getClassLoader();
		URL resource = cl.getResource(filename);
		if (resource == null) {
			throw new NullPointerException("Missing snapshot resource.");
		}
		return resource.getFile();
	}

	@Test
	public void testKeyedCEPFunctionMigration() throws Exception {

		Event startEvent = new Event(42, "start", 1.0);
		SubEvent middleEvent = new SubEvent(42, "foo", 1.0, 10.0);
		Event endEvent=  new Event(42, "end", 1.0);

		TestKeySelector keySelector = new TestKeySelector();

		OneInputStreamOperatorTestHarness<Event, Map<String, Event>> harness =
			new KeyedOneInputStreamOperatorTestHarness<>(
				new KeyedCEPPatternOperator<>(
					Event.createTypeSerializer(),
					false,
					keySelector,
					IntSerializer.INSTANCE,
					new NFAFactory(),
					true),
				keySelector,
				BasicTypeInfo.INT_TYPE_INFO);

		harness.setup();
		harness.initializeStateFromLegacyCheckpoint(getResourceFilename(
			"cep-keyed-savepoint-1.2"));
//			"cep-keyed-savepoint-1.1"));
		harness.open();

		harness.processElement(new StreamRecord<Event>(middleEvent, 3));
		harness.processElement(new StreamRecord<>(new Event(42, "start", 1.0), 4));
		harness.processElement(new StreamRecord<>(endEvent, 5));

		harness.processWatermark(new Watermark(20));

		ConcurrentLinkedQueue<Object> result = harness.getOutput();

		// watermark and the result
		assertEquals(2, result.size());

		Object resultObject = result.poll();
		assertTrue(resultObject instanceof StreamRecord);
		StreamRecord<?> resultRecord = (StreamRecord<?>) resultObject;
		assertTrue(resultRecord.getValue() instanceof Map);

		@SuppressWarnings("unchecked")
		Map<String, Event> patternMap = (Map<String, Event>) resultRecord.getValue();

		assertEquals(startEvent, patternMap.get("start"));
		assertEquals(middleEvent, patternMap.get("middle"));
		assertEquals(endEvent, patternMap.get("end"));

		harness.close();
	}

	@Test
	public void testNonKeyedCEPFunctionMigration() throws Exception {

		Event startEvent = new Event(42, "start", 1.0);
		SubEvent middleEvent = new SubEvent(42, "foo", 1.0, 10.0);
		Event endEvent=  new Event(42, "end", 1.0);

		NullByteKeySelector keySelector = new NullByteKeySelector();

		OneInputStreamOperatorTestHarness<Event, Map<String, Event>> harness =
			new KeyedOneInputStreamOperatorTestHarness<>(
				new KeyedCEPPatternOperator<>(
					Event.createTypeSerializer(),
					false,
					keySelector,
					ByteSerializer.INSTANCE,
					new NFAFactory(),
					false),
				keySelector,
				BasicTypeInfo.BYTE_TYPE_INFO);

		harness.setup();
		harness.initializeStateFromLegacyCheckpoint(getResourceFilename(
			"cep-non-keyed-savepoint-1.2"));
//			"cep-non-keyed-savepoint-1.1"));
		harness.open();

		harness.processElement(new StreamRecord<Event>(middleEvent, 3));
		harness.processElement(new StreamRecord<>(new Event(42, "start", 1.0), 4));
		harness.processElement(new StreamRecord<>(endEvent, 5));

		harness.processWatermark(new Watermark(Long.MAX_VALUE));

		ConcurrentLinkedQueue<Object> result = harness.getOutput();

		// watermark and the result
		assertEquals(2, result.size());

		Object resultObject = result.poll();
		assertTrue(resultObject instanceof StreamRecord);
		StreamRecord<?> resultRecord = (StreamRecord<?>) resultObject;
		assertTrue(resultRecord.getValue() instanceof Map);

		@SuppressWarnings("unchecked")
		Map<String, Event> patternMap = (Map<String, Event>) resultRecord.getValue();

		assertEquals(startEvent, patternMap.get("start"));
		assertEquals(middleEvent, patternMap.get("middle"));
		assertEquals(endEvent, patternMap.get("end"));

		harness.close();
	}

	private static class NFAFactory implements NFACompiler.NFAFactory<Event> {

		private static final long serialVersionUID = 1173020762472766713L;

		private final boolean handleTimeout;

		private NFAFactory() {
			this(false);
		}

		private NFAFactory(boolean handleTimeout) {
			this.handleTimeout = handleTimeout;
		}

		@Override
		public NFA<Event> createNFA() {

			Pattern<Event, ?> pattern = Pattern.<Event>begin("start").where(new CEPMigrationTest.StartFilter())
				.followedBy("middle").subtype(SubEvent.class).where(new CEPMigrationTest.MiddleFilter())
				.followedBy("end").where(new CEPMigrationTest.EndFilter())
				// add a window timeout to test whether timestamps of elements in the
				// priority queue in CEP operator are correctly checkpointed/restored
				.within(Time.milliseconds(10L));

			return NFACompiler.compile(pattern, Event.createTypeSerializer(), handleTimeout);
		}
	}

	private static class StartFilter implements FilterFunction<Event> {
		private static final long serialVersionUID = 5726188262756267490L;

		@Override
		public boolean filter(Event value) throws Exception {
			return value.getName().equals("start");
		}
	}

	private static class MiddleFilter implements FilterFunction<SubEvent> {
		private static final long serialVersionUID = 6215754202506583964L;

		@Override
		public boolean filter(SubEvent value) throws Exception {
			return value.getVolume() > 5.0;
		}
	}

	private static class EndFilter implements FilterFunction<Event> {
		private static final long serialVersionUID = 7056763917392056548L;

		@Override
		public boolean filter(Event value) throws Exception {
			return value.getName().equals("end");
		}
	}

	/**
	 * A simple {@link KeySelector} that returns as key the id of the {@link Event}
	 * provided as argument in the {@link #getKey(Event)}.
	 * */
	private static class TestKeySelector implements KeySelector<Event, Integer> {
		private static final long serialVersionUID = -4873366487571254798L;

		@Override
		public Integer getKey(Event value) throws Exception {
			return value.getId();
		}
	}

	/**
	 * A dummy {@link KeySelector} that returns as key the byte 0. This is
	 * to simulate non-keyed operations.
	 * */
	private static class NullByteKeySelector implements KeySelector<Event, Byte> {

		private static final long serialVersionUID = 614256539098549020L;

		@Override
		public Byte getKey(Event value) throws Exception {
			return 0;
		}
	}
}
