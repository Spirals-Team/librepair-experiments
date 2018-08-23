package com.booking.replication.applier.hbase;

import com.booking.replication.augmenter.model.AugmentedEvent;

import java.util.ArrayList;
import java.util.Collection;

public interface HBaseApplierWriter {

    void buffer(Collection<AugmentedEvent> events);

    long getBufferClearTime();

    int getBufferSize();

    boolean flush();

}
