/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.k8s.api.cache;

public class Event<T> {
    private final EventType eventType;
    private final T obj;

    public Event(EventType eventType, T obj) {
        this.eventType = eventType;
        this.obj = obj;
    }

    public EventType getEventType() {
        return eventType;
    }

    public T getObj() {
        return obj;
    }
}
