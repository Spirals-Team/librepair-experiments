package br.com.zup.eventsourcing.eventstore.domain

import br.com.zup.eventsourcing.core.AggregateId
import br.com.zup.eventsourcing.core.AggregateVersion
import br.com.zup.eventsourcing.core.Event
import br.com.zup.eventsourcing.core.MetaData
import br.com.zup.eventsourcing.eventstore.EventStoreEventHandler
import br.com.zup.eventsourcing.eventstore.EventStoreRepository
import br.com.zup.eventsourcing.eventstore.PersistentAggregateSubscriber
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service


const val SUBSCRIPTION_NAME = "Test"
const val STREAM_PREFIX = "Test-tenant-"
const val STREAM_SUFFIX = "-my-suffix"

@Service
class MyAggregateRepositoryWithOverloadedStreamName : EventStoreRepository<MyAggregateRoot>() {
    override fun getStreamName(aggregateId: AggregateId): String = "$STREAM_PREFIX$aggregateId$STREAM_SUFFIX"
}

@Service
class MyAggregateSubscriberWithOverloadedSubscriptionName(eventHandler: MyEventHandlerWithOverloadedGetAggregateId) :
    PersistentAggregateSubscriber<MyAggregateRoot>(eventHandler = eventHandler) {
    override fun getSubscriptionName(): String = SUBSCRIPTION_NAME
}

@Component
class MyEventHandlerWithOverloadedGetAggregateId : EventStoreEventHandler() {
    lateinit var aggregateId: AggregateId
    lateinit var event: Event
    lateinit var metaData: MetaData

    override fun handle(
        aggregateId: AggregateId,
        event: Event,
        metaData: MetaData,
        version: AggregateVersion
    ) {
        this.aggregateId = aggregateId
        this.event = event
        this.metaData = metaData
    }

    override fun getAggregateId(streamName: String, event: Event): AggregateId {
        return AggregateId(streamName.removePrefix(STREAM_PREFIX).removeSuffix(STREAM_SUFFIX))
    }
}