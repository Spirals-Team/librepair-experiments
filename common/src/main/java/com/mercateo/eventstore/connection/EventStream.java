package com.mercateo.eventstore.connection;

import static com.github.msemys.esjc.ExpectedVersion.ANY;

import java.util.concurrent.CompletableFuture;

import com.github.msemys.esjc.CatchUpSubscription;
import com.github.msemys.esjc.CatchUpSubscriptionListener;
import com.github.msemys.esjc.EventData;
import com.github.msemys.esjc.EventReadResult;
import com.github.msemys.esjc.EventStore;
import com.github.msemys.esjc.WriteResult;
import com.mercateo.eventstore.domain.EventStreamId;

import io.vavr.control.Option;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
public class EventStream {

    public static final int BEFORE_FIRST_EVENT = -1;

    public static final String EVENT_STREAM_ID_SEPARATOR = "/";

    private final EventStreamId eventStreamId;

    private final EventStore eventStoreClient;

    private Option<CatchUpSubscription> catchUpSubscription;

    public EventStream(EventStore eventStoreClient, EventStreamId eventStreamId) {
        this.eventStreamId = eventStreamId;
        this.eventStoreClient = eventStoreClient;
    }

    public CompletableFuture<WriteResult> append(long expectedVersion, EventData eventData) {
        return eventStoreClient.appendToStream(eventStreamId.eventStreamName().value(), expectedVersion, eventData);
    }

    public CompletableFuture<WriteResult> append(long expectedVersion, Iterable<EventData> eventData) {
        return eventStoreClient.appendToStream(eventStreamId.eventStreamName().value(), expectedVersion, eventData);
    }

    public CompletableFuture<WriteResult> append(EventData eventData) {
        return append(ANY, eventData);
    }

    public CompletableFuture<WriteResult> append(Iterable<EventData> eventData) {
        return append(ANY, eventData);
    }

    public void subscribeToStreamFrom(long eventNumber, CatchUpSubscriptionListener eventStreamListener) {
        catchUpSubscription = Option.of(eventStoreClient.subscribeToStreamFrom(eventStreamId.eventStreamName().value(),
                eventNumber == BEFORE_FIRST_EVENT ? null : eventNumber, eventStreamListener));

    }

    public EventStreamId getEventStreamId() {
        return eventStreamId;
    }

    public CompletableFuture<EventReadResult> readEvent(int eventNumber, boolean resolveLinkTos) {
        return eventStoreClient.readEvent(eventStreamId.eventStreamName().value(), eventNumber, resolveLinkTos);
    }

    public void stopSubscription() {
        catchUpSubscription.forEach(CatchUpSubscription::stop);
    }
}
