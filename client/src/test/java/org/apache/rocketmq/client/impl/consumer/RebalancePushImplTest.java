/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rocketmq.client.impl.consumer;

import java.util.HashSet;
import java.util.Set;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.factory.MQClientInstance;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RebalancePushImplTest {
    @Spy
    private DefaultMQPushConsumerImpl defaultMQPushConsumer = new DefaultMQPushConsumerImpl(new DefaultMQPushConsumer("RebalancePushImplTest"), null);
    @Mock
    private MQClientInstance mqClientInstance;
    private String topic = "TopicA";

    @Test
    public void testMessageQueueChanged_CountThreshold() {
        RebalancePushImpl rebalancePush = new RebalancePushImpl(defaultMQPushConsumer);
        rebalancePush.getSubscriptionInner().putIfAbsent(topic, new SubscriptionData());
        rebalancePush.setmQClientFactory(mqClientInstance);

        // Just set pullThresholdForQueue
        defaultMQPushConsumer.getDefaultMQPushConsumer().setPullThresholdForQueue(1024);
        Set<MessageQueue> allocateResultSet = new HashSet<MessageQueue>();
        allocateResultSet.add(new MessageQueue(topic, "BrokerA", 0));
        allocateResultSet.add(new MessageQueue(topic, "BrokerA", 1));
        rebalancePush.messageQueueChanged(topic, new HashSet<MessageQueue>(), allocateResultSet);
        assertThat(defaultMQPushConsumer.getDefaultMQPushConsumer().getPullThresholdForQueue()).isEqualTo(1024);

        // Set pullThresholdForTopic
        defaultMQPushConsumer.getDefaultMQPushConsumer().setPullThresholdForTopic(1024);
        rebalancePush.messageQueueChanged(topic, new HashSet<MessageQueue>(), allocateResultSet);
        assertThat(defaultMQPushConsumer.getDefaultMQPushConsumer().getPullThresholdForQueue()).isEqualTo(512);

        // Change message queue allocate result
        allocateResultSet.add(new MessageQueue(topic, "BrokerA", 2));
        rebalancePush.messageQueueChanged(topic, new HashSet<MessageQueue>(), allocateResultSet);
        assertThat(defaultMQPushConsumer.getDefaultMQPushConsumer().getPullThresholdForQueue()).isEqualTo(341);
    }

    @Test
    public void testMessageQueueChanged_SizeThreshold() {
        RebalancePushImpl rebalancePush = new RebalancePushImpl(defaultMQPushConsumer);
        rebalancePush.getSubscriptionInner().putIfAbsent(topic, new SubscriptionData());
        rebalancePush.setmQClientFactory(mqClientInstance);

        // Just set pullThresholdSizeForQueue
        defaultMQPushConsumer.getDefaultMQPushConsumer().setPullThresholdSizeForQueue(1024);
        Set<MessageQueue> allocateResultSet = new HashSet<MessageQueue>();
        allocateResultSet.add(new MessageQueue(topic, "BrokerA", 0));
        allocateResultSet.add(new MessageQueue(topic, "BrokerA", 1));
        rebalancePush.messageQueueChanged(topic, new HashSet<MessageQueue>(), allocateResultSet);
        assertThat(defaultMQPushConsumer.getDefaultMQPushConsumer().getPullThresholdSizeForQueue()).isEqualTo(1024);

        // Set pullThresholdSizeForTopic
        defaultMQPushConsumer.getDefaultMQPushConsumer().setPullThresholdSizeForTopic(1024);
        rebalancePush.messageQueueChanged(topic, new HashSet<MessageQueue>(), allocateResultSet);
        assertThat(defaultMQPushConsumer.getDefaultMQPushConsumer().getPullThresholdSizeForQueue()).isEqualTo(512);

        // Change message queue allocate result
        allocateResultSet.add(new MessageQueue(topic, "BrokerA", 2));
        rebalancePush.messageQueueChanged(topic, new HashSet<MessageQueue>(), allocateResultSet);
        assertThat(defaultMQPushConsumer.getDefaultMQPushConsumer().getPullThresholdSizeForQueue()).isEqualTo(341);
    }

    @Test
    public void testMessageQueueChanged_ConsumerRuntimeInfo() throws MQClientException {
        RebalancePushImpl rebalancePush = new RebalancePushImpl(defaultMQPushConsumer);
        rebalancePush.getSubscriptionInner().putIfAbsent(topic, new SubscriptionData());
        rebalancePush.setmQClientFactory(mqClientInstance);

        defaultMQPushConsumer.getDefaultMQPushConsumer().setPullThresholdSizeForQueue(1024);
        defaultMQPushConsumer.getDefaultMQPushConsumer().setPullThresholdForQueue(1024);
        Set<MessageQueue> allocateResultSet = new HashSet<MessageQueue>();
        allocateResultSet.add(new MessageQueue(topic, "BrokerA", 0));
        allocateResultSet.add(new MessageQueue(topic, "BrokerA", 1));
        rebalancePush.messageQueueChanged(topic, new HashSet<MessageQueue>(), allocateResultSet);

        defaultMQPushConsumer.setConsumeMessageService(new ConsumeMessageConcurrentlyService(defaultMQPushConsumer, null));
        assertThat(defaultMQPushConsumer.consumerRunningInfo().getProperties().get("pullThresholdSizeForQueue")).isEqualTo("1024");
        assertThat(defaultMQPushConsumer.consumerRunningInfo().getProperties().get("pullThresholdForQueue")).isEqualTo("1024");
        assertThat(defaultMQPushConsumer.consumerRunningInfo().getProperties().get("pullThresholdSizeForTopic")).isEqualTo("-1");
        assertThat(defaultMQPushConsumer.consumerRunningInfo().getProperties().get("pullThresholdForTopic")).isEqualTo("-1");

        defaultMQPushConsumer.getDefaultMQPushConsumer().setPullThresholdSizeForTopic(1024);
        defaultMQPushConsumer.getDefaultMQPushConsumer().setPullThresholdForTopic(1024);
        rebalancePush.messageQueueChanged(topic, new HashSet<MessageQueue>(), allocateResultSet);
        assertThat(defaultMQPushConsumer.consumerRunningInfo().getProperties().get("pullThresholdSizeForQueue")).isEqualTo("512");
        assertThat(defaultMQPushConsumer.consumerRunningInfo().getProperties().get("pullThresholdForQueue")).isEqualTo("512");
        assertThat(defaultMQPushConsumer.consumerRunningInfo().getProperties().get("pullThresholdSizeForTopic")).isEqualTo("1024");
        assertThat(defaultMQPushConsumer.consumerRunningInfo().getProperties().get("pullThresholdForTopic")).isEqualTo("1024");

        // Change message queue allocate result
        allocateResultSet.add(new MessageQueue(topic, "BrokerA", 2));
        rebalancePush.messageQueueChanged(topic, new HashSet<MessageQueue>(), allocateResultSet);
        assertThat(defaultMQPushConsumer.consumerRunningInfo().getProperties().get("pullThresholdSizeForQueue")).isEqualTo("341");
        assertThat(defaultMQPushConsumer.consumerRunningInfo().getProperties().get("pullThresholdForQueue")).isEqualTo("341");
        assertThat(defaultMQPushConsumer.consumerRunningInfo().getProperties().get("pullThresholdSizeForTopic")).isEqualTo("1024");
        assertThat(defaultMQPushConsumer.consumerRunningInfo().getProperties().get("pullThresholdForTopic")).isEqualTo("1024");
    }
}