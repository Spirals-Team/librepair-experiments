package br.com.zup.eventsourcing.eventstore

import br.com.zup.eventsourcing.core.AggregateId
import br.com.zup.eventsourcing.eventstore.config.BaseTest
import br.com.zup.eventsourcing.eventstore.domain.MyAggregateRepositoryWithOverloadedStreamName
import br.com.zup.eventsourcing.eventstore.domain.MyAggregateRoot
import br.com.zup.eventsourcing.eventstore.domain.MyAggregateSubscriberWithOverloadedSubscriptionName
import br.com.zup.eventsourcing.eventstore.domain.MyEventHandlerWithOverloadedGetAggregateId
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import kotlin.test.assertNotNull

class OverridesTest : BaseTest() {

    @Autowired
    lateinit var myAggregateRepository: MyAggregateRepositoryWithOverloadedStreamName
    @Autowired
    lateinit var myAggregateSubscriber: MyAggregateSubscriberWithOverloadedSubscriptionName
    @Autowired
    lateinit var eventHandler: MyEventHandlerWithOverloadedGetAggregateId

    @Before
    fun setUp() {
        myAggregateSubscriber.start()
    }

    @Test
    fun saveMyAggregate_WithoutMetaData() {
        val myAggregate = createMyAggregate()
        assertEquals(1, myAggregate.events.size)
        assertReceivedEventWithCorrectAggregateId(myAggregate)
    }

    @Test
    fun saveMyAggregateCreateAndGet() {
        val myAggregate = createMyAggregate()
        val myAggregateGot = myAggregateRepository.get(myAggregate.id)
        assertEquals(myAggregate, myAggregateGot)
        assertEquals(1, myAggregate.events.size)
        assertEquals(0, myAggregateGot.events.size)
        assertReceivedEventWithCorrectAggregateId(myAggregate)
    }

    @Test
    fun createAndModifyAggregate() {
        val id = UUID.randomUUID()

        val myAggregate = MyAggregateRoot(AggregateId(id))
        myAggregate.modify()
        myAggregate.modify()
        myAggregateRepository.save(myAggregate)

        val loadedFromEventStore = myAggregateRepository.get(myAggregate.id)

        assertEquals(myAggregate, loadedFromEventStore)
        assertEquals("ModifyEvent", loadedFromEventStore.status)
        assertEquals(2, loadedFromEventStore.modificationHistory.size)
        assertEquals(2, loadedFromEventStore.version.value)
        assertEquals(3, myAggregate.events.size)
        assertEquals(0, loadedFromEventStore.events.size)
        assertReceivedEventWithCorrectAggregateId(myAggregate)
    }

    private fun assertReceivedEventWithCorrectAggregateId(myAggregate: MyAggregateRoot) {
        for (i in 0..10) Thread.sleep(200)
        assertNotNull(eventHandler.aggregateId)
        assertEquals(myAggregate.id, eventHandler.aggregateId)
    }

    private fun createMyAggregate(): MyAggregateRoot {
        val id = UUID.randomUUID()
        val myAggregate = MyAggregateRoot(AggregateId(id))
        myAggregateRepository.save(myAggregate)
        return myAggregate
    }
}