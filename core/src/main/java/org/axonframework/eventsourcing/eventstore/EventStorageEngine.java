/*
 * Copyright (c) 2010-2016. Axon Framework
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.eventsourcing.eventstore;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.TrackedEventMessage;
import org.axonframework.eventsourcing.DomainEventMessage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

/**
 * Provides a mechanism to append as well as retrieve events from an underlying storage like a database. An event
 * storage engine can also be used to store and fetch aggregate snapshot events.
 *
 * @author Rene de Waele
 */
public interface EventStorageEngine {

    /**
     * Append one or more events to the event storage. Events will be appended in the order that they are offered in.
     * <p>
     * Note that all events should have a unique event identifier. When storing {@link DomainEventMessage domain events}
     * events should also have a unique combination of aggregate id and sequence number.
     * <p>
     * By default this method creates a list of the offered events and then invokes {@link #appendEvents(List)}.
     *
     * @param events Events to append to the event storage
     */
    default void appendEvents(EventMessage<?>... events) {
        appendEvents(asList(events));
    }

    /**
     * Append a list of events to the event storage. Events will be appended in the order that they are offered in.
     * <p>
     * Note that all events should have a unique event identifier. When storing {@link DomainEventMessage domain events}
     * events should also have a unique combination of aggregate id and sequence number.
     *
     * @param events Events to append to the event storage
     */
    void appendEvents(List<? extends EventMessage<?>> events);

    /**
     * Store an event that contains a snapshot of an aggregate. If the event storage already contains a snapshot for the
     * same aggregate, then it will be replaced with the given snapshot.
     *
     * @param snapshot The snapshot event of the aggregate that is to be stored
     */
    void storeSnapshot(DomainEventMessage<?> snapshot);

    /**
     * Open an event stream containing all events stored since given tracking token. The returned stream is comprised of
     * events from aggregates as well as other application events. Pass a {@code trackingToken} of {@code null} to open
     * a stream containing all available events.
     * <p>
     * If the value of the given {@code mayBlock} is {@code true} the returned stream is allowed to block while waiting
     * for new event messages if the end of the stream is reached.
     *
     * @param trackingToken Object describing the global index of the last processed event or {@code null} to create a
     *                      stream of all events in the store
     * @param mayBlock      If {@code true} the storage engine may optionally choose to block to wait for new event
     *                      messages if the end of the stream is reached.
     * @return A stream containing all tracked event messages stored since the given tracking token
     */
    Stream<? extends TrackedEventMessage<?>> readEvents(TrackingToken trackingToken, boolean mayBlock);

    /**
     * Get a {@link DomainEventStream} containing all events published by the aggregate with given {@code
     * aggregateIdentifier}. By default calling this method is shorthand for an invocation of
     * {@link #readEvents(String, long)} with a sequence number of 0.
     * <p>
     * The returned stream is finite, i.e. it should not block to wait for further events if the end of the event stream
     * of the aggregate is reached.
     *
     * @param aggregateIdentifier The identifier of the aggregate to return an event stream for
     * @return A non-blocking DomainEventStream of the given aggregate
     */
    default DomainEventStream readEvents(String aggregateIdentifier) {
        return readEvents(aggregateIdentifier, 0L);
    }

    /**
     * Get a {@link DomainEventStream} containing all events published by the aggregate with given {@code
     * aggregateIdentifier} starting with the first event having a sequence number that is equal or larger than the
     * given {@code firstSequenceNumber}.
     * <p>
     * The returned stream is finite, i.e. it should not block to wait for further
     * events if the end of the event stream of the aggregate is reached.
     *
     * @param aggregateIdentifier The identifier of the aggregate
     * @param firstSequenceNumber The expected sequence number of the first event in the returned stream
     * @return A non-blocking DomainEventStream of the given aggregate
     */
    DomainEventStream readEvents(String aggregateIdentifier, long firstSequenceNumber);

    /**
     * Try to load a snapshot event of the aggregate with given {@code aggregateIdentifier}. If the storage engine has
     * no snapshot event of the aggregate, an empty Optional is returned.
     *
     * @param aggregateIdentifier The identifier of the aggregate
     * @return An optional with a snapshot of the aggregate
     */
    Optional<DomainEventMessage<?>> readSnapshot(String aggregateIdentifier);
}
