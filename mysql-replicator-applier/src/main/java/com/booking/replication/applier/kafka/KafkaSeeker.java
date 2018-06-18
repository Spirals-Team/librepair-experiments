package com.booking.replication.applier.kafka;

import com.booking.replication.applier.Partitioner;
import com.booking.replication.applier.Seeker;
import com.booking.replication.augmenter.model.AugmentedEvent;
import com.booking.replication.augmenter.model.AugmentedEventHeader;
import com.booking.replication.commons.checkpoint.Checkpoint;

import com.booking.replication.commons.map.MapFilter;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.InvalidPartitionsException;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KafkaSeeker implements Seeker {
    public interface Configuration {
        String TOPIC = "kafka.topic";
        String CONSUMER_PREFIX = "kafka.consumer.";
    }

    private static final Logger LOG = Logger.getLogger(KafkaSeeker.class.getName());
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final String topic;
    private final Partitioner partitioner;
    private final Map<String, Object> configuration;
    private final AtomicBoolean sought;

    private int totalPartitions;
    private Checkpoint[] partitionCheckpoint;
    private BitSet partitionSought;

    public KafkaSeeker(Map<String, Object> configuration) {
        Object topic = configuration.get(Configuration.TOPIC);

        Objects.requireNonNull(topic, String.format("Configuration required: %s", Configuration.TOPIC));

        this.topic = topic.toString();
        this.partitioner = Partitioner.build(configuration);
        this.configuration = new MapFilter(configuration).filter(Configuration.CONSUMER_PREFIX);
        this.sought = new AtomicBoolean();
    }

    @Override
    public Checkpoint seek(Checkpoint checkpoint) {
        KafkaSeeker.LOG.log(Level.INFO,"seeking for last events");

        try (Consumer<byte[], byte[]> consumer = new KafkaConsumer<>(this.configuration, new ByteArrayDeserializer(), new ByteArrayDeserializer())) {
            this.totalPartitions = consumer.partitionsFor(this.topic).stream().mapToInt(PartitionInfo::partition).max().orElseThrow(() -> new InvalidPartitionsException("partitions not found")) + 1;
            this.partitionCheckpoint = new Checkpoint[this.totalPartitions];
            this.partitionSought = new BitSet(this.totalPartitions);

            Arrays.fill(this.partitionCheckpoint, checkpoint);

            Checkpoint minimumCheckpoint = checkpoint;

            consumer.subscribe(Arrays.asList(this.topic));
            consumer.poll(100L);

            Map<TopicPartition, Long>  endOffsetMap = consumer.endOffsets(consumer.assignment());

            for (Map.Entry<TopicPartition, Long> endOffsetEntry : endOffsetMap.entrySet()) {
                long endOffset = endOffsetEntry.getValue();

                if (endOffset > 0) {
                    consumer.seek(endOffsetEntry.getKey(), endOffset - 1);

                    ConsumerRecords<byte[], byte[]> consumerRecords = consumer.poll(100);

                    for (ConsumerRecord<byte[], byte[]> consumerRecord : consumerRecords) {
                        int partition = consumerRecord.partition();

                        Checkpoint currentCheckpoint = KafkaSeeker.MAPPER.readValue(
                                consumerRecord.key(), AugmentedEventHeader.class
                        ).getCheckpoint();

                        if (this.partitionCheckpoint[partition] == null || this.partitionCheckpoint[partition].compareTo(currentCheckpoint) < 0) {
                            this.partitionCheckpoint[partition] = currentCheckpoint;
                        }

                        if (minimumCheckpoint == null || minimumCheckpoint.compareTo(currentCheckpoint) > 0) {
                            minimumCheckpoint = currentCheckpoint;
                        }
                    }
                }
            }

            this.sought.set(false);

            return minimumCheckpoint;
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    @Override
    public List<AugmentedEvent> apply(List<AugmentedEvent> augmentedEventList) {
        if (this.sought.get()) {
            return augmentedEventList;
        } else {
            List<AugmentedEvent> soughtAugmentedEventList = new ArrayList<>();

            for (AugmentedEvent augmentedEvent : augmentedEventList) {
                int partition = this.partitioner.apply(augmentedEvent, this.totalPartitions);

                if (this.partitionSought.get(partition)) {
                    soughtAugmentedEventList.add(augmentedEvent);
                } else if (this.partitionCheckpoint[partition] == null || this.partitionCheckpoint[partition].compareTo(augmentedEvent.getHeader().getCheckpoint()) < 0) {
                    this.partitionSought.set(partition);
                    this.sought.set(this.partitionSought.cardinality() == this.totalPartitions);

                    soughtAugmentedEventList.add(augmentedEvent);

                    KafkaSeeker.LOG.log(Level.INFO, String.format("sought partition %d", partition));
                }
            }

            if (soughtAugmentedEventList.size() > 0) {
                return soughtAugmentedEventList;
            } else {
                return null;
            }
        }
    }
}
