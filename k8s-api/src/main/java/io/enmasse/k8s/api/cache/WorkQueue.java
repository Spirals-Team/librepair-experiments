/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api.cache;

import java.util.concurrent.TimeUnit;

public interface WorkQueue<T> {
    void add(Event<T> event);
    void pop(Processor<Event<T>> processor, long timeout, TimeUnit timeUnit) throws InterruptedException;
}
