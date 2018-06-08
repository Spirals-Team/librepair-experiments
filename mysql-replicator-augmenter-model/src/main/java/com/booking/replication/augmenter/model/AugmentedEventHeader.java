package com.booking.replication.augmenter.model;

import com.booking.replication.commons.checkpoint.Checkpoint;

import java.io.Serializable;

@SuppressWarnings("unused")
public class AugmentedEventHeader implements Serializable {
    private long timestamp;
    private Checkpoint checkpoint;
    private AugmentedEventType eventType;
    private AugmentedEventTable eventTable;

    public AugmentedEventHeader() {
    }

    public AugmentedEventHeader(long timestamp, Checkpoint checkpoint, AugmentedEventType eventType, AugmentedEventTable eventTable) {
        this.timestamp = timestamp;
        this.checkpoint = checkpoint;
        this.eventType = eventType;
        this.eventTable = eventTable;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public Checkpoint getCheckpoint() {
        return this.checkpoint;
    }

    public AugmentedEventType getEventType() {
        return this.eventType;
    }

    public AugmentedEventTable getEventTable() {
        return this.eventTable;
    }
}

