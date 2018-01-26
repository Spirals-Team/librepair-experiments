/*
 * Licensed to the Apache Software Foundation (ASF) under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ASF licenses this file
 *   to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.apache.storm.kafka.spout;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.storm.kafka.KafkaUnitRule;
import org.apache.storm.kafka.spout.builders.SingleTopicKafkaSpoutConfiguration;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.tuple.Values;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

import static org.apache.storm.kafka.spout.builders.SingleTopicKafkaSpoutConfiguration.getKafkaSpoutConfig;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SingleTopicKafkaSpoutTest {

    private class SpoutContext {
        public KafkaSpout<String, String> spout;
        public SpoutOutputCollector collector;

        public SpoutContext(KafkaSpout<String, String> spout,
                            SpoutOutputCollector collector) {
            this.spout = spout;
            this.collector = collector;
        }
    }

    @Rule
    public KafkaUnitRule kafkaUnitRule = new KafkaUnitRule();

    void populateTopicData(String topicName, int msgCount) throws InterruptedException, ExecutionException, TimeoutException {
        kafkaUnitRule.getKafkaUnit().createTopic(topicName);

        for (int i = 0; i < msgCount; i++) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                    topicName, Integer.toString(i),
                    Integer.toString(i));
            kafkaUnitRule.getKafkaUnit().sendMessage(producerRecord);
        }
    }

    SpoutContext initializeSpout(int msgCount) throws InterruptedException, ExecutionException, TimeoutException {
        populateTopicData(SingleTopicKafkaSpoutConfiguration.TOPIC, msgCount);
        int kafkaPort = kafkaUnitRule.getKafkaUnit().getKafkaPort();

        TopologyContext topology = mock(TopologyContext.class);
        SpoutOutputCollector collector = mock(SpoutOutputCollector.class);
        Map conf = mock(Map.class);

        KafkaSpout<String, String> spout = new KafkaSpout<>(getKafkaSpoutConfig(kafkaPort));
        spout.open(conf, topology, collector);
        spout.activate();
        return new SpoutContext(spout, collector);
    }

    /*
     * Asserts that the next possible offset to commit or the committed offset is the provided offset.
     * An offset that is ready to be committed is not guarenteed to be already committed.
     */
    private void assertOffsetCommitted(int offset, KafkaSpout.OffsetEntry entry) {

        boolean currentOffsetMatch = entry.getCommittedOffset() == offset;
        OffsetAndMetadata nextOffset = entry.findNextCommitOffset();
        boolean nextOffsetMatch = nextOffset != null && nextOffset.offset() == offset;
        assertTrue("Next offset: " +
                   entry.findNextCommitOffset() +
                   " OR current offset: " +
                   entry.getCommittedOffset() +
                   " must equal desired offset: " +
                   offset,
                   currentOffsetMatch | nextOffsetMatch);
    }

    @Test
    public void shouldContinueWithSlowDoubleAcks() throws Exception {
        int messageCount = 20;
        SpoutContext context = initializeSpout(messageCount);

        //play 1st tuple
        ArgumentCaptor<Object> messageIdToDoubleAck = ArgumentCaptor.forClass(Object.class);
        context.spout.nextTuple();
        verify(context.collector).emit(anyObject(), anyObject(), messageIdToDoubleAck.capture());
        context.spout.ack(messageIdToDoubleAck.getValue());

        IntStream.range(0, messageCount / 2).forEach(value -> {
            context.spout.nextTuple();
        });

        context.spout.ack(messageIdToDoubleAck.getValue());

        IntStream.range(0, messageCount).forEach(value -> {
            context.spout.nextTuple();
        });

        ArgumentCaptor<Object> remainingIds = ArgumentCaptor.forClass(Object.class);

        verify(context.collector, times(messageCount)).emit(
                eq(SingleTopicKafkaSpoutConfiguration.STREAM),
                anyObject(),
                remainingIds.capture());
        remainingIds.getAllValues().iterator().forEachRemaining(context.spout::ack);

        context.spout.acked.values().forEach(item -> {
            assertOffsetCommitted(messageCount - 1, (KafkaSpout.OffsetEntry) item);
        });
    }

    @Test
    public void shouldEmitAllMessages() throws Exception {
        int messageCount = 10;
        SpoutContext context = initializeSpout(messageCount);


        IntStream.range(0, messageCount).forEach(value -> {
            context.spout.nextTuple();
            ArgumentCaptor<Object> messageId = ArgumentCaptor.forClass(Object.class);
            verify(context.collector).emit(
                    eq(SingleTopicKafkaSpoutConfiguration.STREAM),
                    eq(new Values(SingleTopicKafkaSpoutConfiguration.TOPIC,
                                  Integer.toString(value),
                                  Integer.toString(value))),
                    messageId.capture());
            context.spout.ack(messageId.getValue());
            reset(context.collector);
        });

        context.spout.acked.values().forEach(item -> {
            assertOffsetCommitted(messageCount - 1, (KafkaSpout.OffsetEntry) item);
        });
    }

    @Test
    public void shouldReplayInOrderFailedMessages() throws Exception {
        int messageCount = 10;
        SpoutContext context = initializeSpout(messageCount);

        //play and ack 1 tuple
        ArgumentCaptor<Object> messageIdAcked = ArgumentCaptor.forClass(Object.class);
        context.spout.nextTuple();
        verify(context.collector).emit(anyObject(), anyObject(), messageIdAcked.capture());
        context.spout.ack(messageIdAcked.getValue());
        reset(context.collector);

        //play and fail 1 tuple
        ArgumentCaptor<Object> messageIdFailed = ArgumentCaptor.forClass(Object.class);
        context.spout.nextTuple();
        verify(context.collector).emit(anyObject(), anyObject(), messageIdFailed.capture());
        context.spout.fail(messageIdFailed.getValue());
        reset(context.collector);

        //pause so that failed tuples will be retried
        Thread.sleep(200);


        //allow for some calls to nextTuple() to fail to emit a tuple
        IntStream.range(0, messageCount + 5).forEach(value -> {
            context.spout.nextTuple();
        });

        ArgumentCaptor<Object> remainingMessageIds = ArgumentCaptor.forClass(Object.class);

        //1 message replayed, messageCount - 2 messages emitted for the first time
        verify(context.collector, times(messageCount - 1)).emit(
                eq(SingleTopicKafkaSpoutConfiguration.STREAM),
                anyObject(),
                remainingMessageIds.capture());
        remainingMessageIds.getAllValues().iterator().forEachRemaining(context.spout::ack);

        context.spout.acked.values().forEach(item -> {
            assertOffsetCommitted(messageCount - 1, (KafkaSpout.OffsetEntry) item);
        });
    }

    @Test
    public void shouldReplayFirstTupleFailedOutOfOrder() throws Exception {
        int messageCount = 10;
        SpoutContext context = initializeSpout(messageCount);


        //play 1st tuple
        ArgumentCaptor<Object> messageIdToFail = ArgumentCaptor.forClass(Object.class);
        context.spout.nextTuple();
        verify(context.collector).emit(anyObject(), anyObject(), messageIdToFail.capture());
        reset(context.collector);

        //play 2nd tuple
        ArgumentCaptor<Object> messageIdToAck = ArgumentCaptor.forClass(Object.class);
        context.spout.nextTuple();
        verify(context.collector).emit(anyObject(), anyObject(), messageIdToAck.capture());
        reset(context.collector);

        //ack 2nd tuple
        context.spout.ack(messageIdToAck.getValue());
        //fail 1st tuple
        context.spout.fail(messageIdToFail.getValue());

        //pause so that failed tuples will be retried
        Thread.sleep(200);

        //allow for some calls to nextTuple() to fail to emit a tuple
        IntStream.range(0, messageCount + 5).forEach(value -> {
            context.spout.nextTuple();
        });

        ArgumentCaptor<Object> remainingIds = ArgumentCaptor.forClass(Object.class);
        //1 message replayed, messageCount - 2 messages emitted for the first time
        verify(context.collector, times(messageCount - 1)).emit(
                eq(SingleTopicKafkaSpoutConfiguration.STREAM),
                anyObject(),
                remainingIds.capture());
        remainingIds.getAllValues().iterator().forEachRemaining(context.spout::ack);

        context.spout.acked.values().forEach(item -> {
            assertOffsetCommitted(messageCount - 1, (KafkaSpout.OffsetEntry) item);
        });
    }
}