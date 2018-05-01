package br.com.zup.eventsourcing.eventstore

import br.com.zup.eventsourcing.core.AggregateId
import br.com.zup.eventsourcing.eventstore.domain.CreateEvent
import br.com.zup.eventsourcing.eventstore.domain.MyEventHandler
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class MyEventHandlerTest {

    private lateinit var eventHandler: MyEventHandler

    @Before
    fun setUp() {
        this.eventHandler = MyEventHandler()
    }

    @Test
    fun shouldGetAggregateIdFromEventAggregateId_whenItExistsOnEvent() {
        val aggregateId = AggregateId("ID")
        val event = CreateEvent(aggregateId)
        event.eventAggregateId = aggregateId
        assertEquals(aggregateId, eventHandler.getAggregateId("prefix-ID-suffix", event))
    }

    @Test
    fun shouldGetAggregateIdFromStream_whenEventAggregateIdDoesNotExistOnEvent() {
        val aggregateId = AggregateId("tenant-ID-suffix")
        val event = CreateEvent(aggregateId)
        assertEquals(aggregateId, eventHandler.getAggregateId("prefix-tenant-ID-suffix", event))
    }
}