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

package org.apache.flink.streaming.connectors.kafka;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.TypeInformationSerializationSchema;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.operators.StreamSink;
import org.apache.flink.streaming.connectors.kafka.partitioner.FlinkKafkaPartitioner;
import org.apache.flink.streaming.connectors.kafka.testutils.FailingIdentityMapper;
import org.apache.flink.streaming.connectors.kafka.testutils.IntegerSource;
import org.apache.flink.streaming.util.serialization.KeyedSerializationSchema;
import org.apache.flink.streaming.util.serialization.KeyedSerializationSchemaWrapper;
import org.apache.flink.test.util.TestUtils;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

/**
 * IT cases for the {@link FlinkKafkaProducer011}.
 */
@SuppressWarnings("serial")
public class Kafka011ProducerExactlyOnceITCase extends KafkaProducerTestBase {
	@BeforeClass
	public static void prepare() throws ClassNotFoundException {
		KafkaProducerTestBase.prepare();
		((KafkaTestEnvironmentImpl) kafkaServer).setProducerSemantic(FlinkKafkaProducer011.Semantic.EXACTLY_ONCE);
	}

	@Override
	public void testOneToOneAtLeastOnceRegularSink() throws Exception {
		// TODO: fix this test
		// currently very often (~50% cases) KafkaProducer live locks itself on commitTransaction call.
		// Somehow Kafka 0.11 doesn't play along with NetworkFailureProxy. This can either mean a bug in Kafka
		// that it doesn't work well with some weird network failures, or the NetworkFailureProxy is a broken design
		// and this test should be reimplemented in completely different way...
	}

	@Override
	public void testOneToOneAtLeastOnceCustomOperator() throws Exception {
		// TODO: fix this test
		// currently very often (~50% cases) KafkaProducer live locks itself on commitTransaction call.
		// Somehow Kafka 0.11 doesn't play along with NetworkFailureProxy. This can either mean a bug in Kafka
		// that it doesn't work well with some weird network failures, or the NetworkFailureProxy is a broken design
		// and this test should be reimplemented in completely different way...
	}

	@Test
	public void testMultipleSinkOperators() throws Exception {
		final String topic = false ? "exactlyOnceTopicRegularSink" : "exactlyTopicCustomOperator";
		final int partition = 0;
		final int numElements = 1000;
		final int failAfterElements = 333;

		createTestTopic(topic, 1, 1);

		TypeInformationSerializationSchema<Integer> schema = new TypeInformationSerializationSchema<>(BasicTypeInfo.INT_TYPE_INFO, new ExecutionConfig());
		KeyedSerializationSchema<Integer> keyedSerializationSchema = new KeyedSerializationSchemaWrapper(schema);

		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		env.enableCheckpointing(500);
		env.setParallelism(1);
		env.setRestartStrategy(RestartStrategies.fixedDelayRestart(1, 0));
		env.getConfig().disableSysoutLogging();

		Properties properties = new Properties();
		properties.putAll(standardProps);
		properties.putAll(secureProps);

		// process exactly failAfterElements number of elements and then shutdown Kafka broker and fail application
		List<Integer> expectedElements = getIntegersSequence(numElements);

		for (int i = 0; i < 2; i++) {
			DataStream<Integer> inputStream = env
				.addSource(new IntegerSource(numElements))
				.map(new FailingIdentityMapper<Integer>(failAfterElements));

			FlinkKafkaPartitioner<Integer> partitioner = new FlinkKafkaPartitioner<Integer>() {
				@Override
				public int partition(Integer record, byte[] key, byte[] value, String targetTopic, int[] partitions) {
					return partition;
				}
			};

			if (false) {
				StreamSink<Integer> kafkaSink = kafkaServer.getProducerSink(topic + i, keyedSerializationSchema, properties, partitioner);
				inputStream.addSink(kafkaSink.getUserFunction());
			} else {
				kafkaServer.produceIntoKafka(inputStream, topic + i, keyedSerializationSchema, properties, partitioner);
			}
		}

		FailingIdentityMapper.failedBefore = false;
		TestUtils.tryExecute(env, "Exactly once test");

		for (int i = 0; i < 2; i++) {
			// assert that before failure we successfully snapshot/flushed all expected elements
			assertExactlyOnceForTopic(
				properties,
				topic + i,
				partition,
				expectedElements,
				KAFKA_READ_TIMEOUT);
			deleteTestTopic(topic);
		}
	}
}
