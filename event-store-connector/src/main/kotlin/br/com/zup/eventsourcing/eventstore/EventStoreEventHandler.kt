package br.com.zup.eventsourcing.eventstore

import br.com.zup.eventsourcing.core.*

abstract class EventStoreEventHandler : EventHandler {

    open fun getAggregateId(streamName: String, event: Event): AggregateId {
        return event.eventAggregateId ?: AggregateId(streamName.substringAfter("-"))
    }
}