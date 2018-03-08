/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api.cache;

import io.fabric8.kubernetes.api.model.HasMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class FifoQueue<T extends HasMetadata> implements WorkQueue<T> {
    private static final Logger log = LoggerFactory.getLogger(FifoQueue.class);

    private final BlockingQueue<Event<T>> queue;

    public FifoQueue(int maxSize) {
        queue = new ArrayBlockingQueue<>(maxSize);
    }

    @Override
    public void add(Event<T> t) {
        if (!queue.offer(t)) {
            log.warn("FIFO full, skipping event {} for {}", t.getEventType(), t.getObj().getMetadata().getName());
        }
    }

    @Override
    public void pop(Processor<Event<T>> processor, long timeout, TimeUnit timeUnit) throws InterruptedException {
        Event<T> event = queue.poll(timeout, timeUnit);
        if (event != null) {
            processor.process(event);
        }
    }
}
