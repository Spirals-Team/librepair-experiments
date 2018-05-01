package br.com.zup.eventsourcing.eventstore

import br.com.zup.eventsourcing.core.AggregateId
import br.com.zup.eventsourcing.core.AggregateVersion
import br.com.zup.eventsourcing.core.MetaData
import br.com.zup.eventsourcing.core.Repository
import br.com.zup.eventsourcing.eventstore.config.BaseTest
import br.com.zup.eventsourcing.eventstore.domain.ModifyEvent
import br.com.zup.eventsourcing.eventstore.domain.MyAggregateRepository
import br.com.zup.eventsourcing.eventstore.domain.MyAggregateRoot
import eventstore.WrongExpectedVersionException
import org.junit.Assert.assertEquals
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import kotlin.test.assertTrue

class MyAggregateRootRepositoryTest : BaseTest() {

    @Autowired
    lateinit var myAggregateRepository: MyAggregateRepository


    @Test
    fun saveMyAggregate_WithoutMetaData() {
        val id = UUID.randomUUID()
        val myAggregate = MyAggregateRoot(AggregateId(id))
        myAggregateRepository.save(myAggregate)
        assertEquals(1, myAggregate.events.size)
    }

    @Test
    fun saveMyAggregate_WithMetaData() {
        val id = UUID.randomUUID()
        val myAggregate = MyAggregateRoot(AggregateId(id))
        val metaData = MetaData()
        metaData.set("teste", myAggregate)
        myAggregateRepository.save(myAggregate, metaData)
        assertEquals(1, myAggregate.events.size)
    }

    @Test
    fun saveMyAggregateCreateAndGet() {
        val id = UUID.randomUUID()
        val myAggregate = MyAggregateRoot(AggregateId(id))
        val metaData = MetaData()
        metaData.set("teste", myAggregate)
        myAggregateRepository.save(myAggregate, metaData)
        val myAggregateGot = myAggregateRepository.get(myAggregate.id)
        assertEquals(myAggregate, myAggregateGot)
        assertEquals(1, myAggregate.events.size)
        assertEquals(0, myAggregateGot.events.size)
    }

    @Test
    fun saveMyAggregateWithTenantCreateAndGet() {
        val id = UUID.randomUUID()
        val tenant = "Tenant"
        val myAggregate = MyAggregateRoot(AggregateId(id, tenant))
        val metaData = MetaData()
        metaData.set("teste", myAggregate)
        myAggregateRepository.save(myAggregate, metaData)
        val myAggregateGot = myAggregateRepository.get(myAggregate.id)
        assertEquals(myAggregate, myAggregateGot)
        assertEquals(1, myAggregate.events.size)
        assertEquals(0, myAggregateGot.events.size)
    }

    @Test
    fun createAndModifyAggregate() {
        val id = UUID.randomUUID()

        val myAggregate = MyAggregateRoot(AggregateId(id))
        myAggregate.modify()
        myAggregate.modify()
        val metaData = MetaData()
        metaData.set("teste2", myAggregate)
        myAggregateRepository.save(myAggregate, metaData)

        val loadedFromEventStore = myAggregateRepository.get(myAggregate.id)

        assertEquals(myAggregate, loadedFromEventStore)
        assertEquals("ModifyEvent", loadedFromEventStore.status)
        assertEquals(2, loadedFromEventStore.modificationHistory.size)
        assertEquals(2, loadedFromEventStore.version.value)
        assertEquals(3, myAggregate.events.size)
        assertEquals(0, loadedFromEventStore.events.size)

    }

    @Test
    fun createAndModifyAggregateWithTenant() {
        val id = UUID.randomUUID()
        val tenant = "tenant"
        val myAggregate = MyAggregateRoot(AggregateId(id, tenant))
        myAggregate.modify()
        myAggregate.modify()
        val metaData = MetaData()
        metaData.set("teste2", myAggregate)
        myAggregateRepository.save(myAggregate, metaData)

        val loadedFromEventStore = myAggregateRepository.get(myAggregate.id)

        assertEquals(myAggregate, loadedFromEventStore)
        assertEquals("ModifyEvent", loadedFromEventStore.status)
        assertEquals(2, loadedFromEventStore.modificationHistory.size)
        assertEquals(2, loadedFromEventStore.version.value)
        assertEquals(3, myAggregate.events.size)
        assertEquals(0, loadedFromEventStore.events.size)

    }

    @Test
    fun saveModifyAndThenSaveAgain() {
        val id = UUID.randomUUID()
        val tenant = "tenant"
        var myAggregate = MyAggregateRoot(AggregateId(id, tenant))
        val metaData = MetaData().apply { set("teste2", myAggregate) }
        myAggregate.modify()
        myAggregate.modify()
        myAggregate = myAggregateRepository.save(myAggregate, metaData, Repository.OptimisticLock.ENABLED)
        myAggregate.modify2()
        myAggregate = myAggregateRepository.save(myAggregate, metaData, Repository.OptimisticLock.ENABLED)

        val loadedFromEventStore = myAggregateRepository.get(myAggregate.id)
        assertEquals(myAggregate, loadedFromEventStore)
        assertEquals("ModifyEvent2", loadedFromEventStore.status)
        assertEquals(3, loadedFromEventStore.modificationHistory.size)
        assertEquals(3, loadedFromEventStore.version.value)
        assertEquals(0, myAggregate.events.size)
        assertEquals(0, loadedFromEventStore.events.size)
        assertEquals(loadedFromEventStore.event, myAggregate.event)
    }

    @Test(expected = WrongExpectedVersionException::class)
    fun saveWithWrongExpectedVersion() {
        val id = UUID.randomUUID()
        val myAggregate = MyAggregateRoot(AggregateId(id))
        val metaData = MetaData()
        metaData.set("teste2", myAggregate)
        myAggregate.version = AggregateVersion(3)
        myAggregateRepository.save(myAggregate, metaData)
        assertEquals(1, myAggregate.events.size)
    }

    @Test
    fun saveWithoutOptimisticLock() {
        val id = UUID.randomUUID()
        val myAggregate = MyAggregateRoot(AggregateId(id))
        val metaData = MetaData()
        metaData.set("teste2", myAggregate)
        myAggregateRepository.save(myAggregate, metaData)

        val agg1 = myAggregateRepository.get(myAggregate.id)
        val agg2 = myAggregateRepository.get(myAggregate.id)
        assertEquals(agg1.version, agg2.version)

        agg1.modify()
        myAggregateRepository.save(agg1, metaData, Repository.OptimisticLock.DISABLED)

        agg2.modify()
        myAggregateRepository.save(agg2, metaData, Repository.OptimisticLock.DISABLED)

        assertEquals(AggregateVersion(2), myAggregateRepository.get(myAggregate.id).version)
    }

    @Test
    fun saveMyAggregateCreateNoModifyAndSaveAgain() {
        val id = UUID.randomUUID()
        val myAggregate = MyAggregateRoot(AggregateId(id))
        val metaData = MetaData()
        metaData.set("teste", "testeSaveAgain")
        myAggregateRepository.save(myAggregate, metaData)
        assertEquals(1, myAggregate.events.size)
        myAggregateRepository.save(myAggregate, metaData)
        assertEquals(1, myAggregate.events.size)
    }

    @Test
    fun getMetaData_whenOnlyOneEvent() {
        val aggregateID = AggregateId(UUID.randomUUID())
        val myAggregate = MyAggregateRoot(aggregateID)
        val metaData = MetaData()
        metaData.set("teste", "teste")
        myAggregateRepository.save(myAggregate, metaData)
        assertEquals(1, myAggregate.events.size)
        val myRetrievedMetaData = myAggregateRepository.getLastMetaData(aggregateID)
        assertEquals(metaData["teste"], myRetrievedMetaData["teste"])
    }

    @Test
    fun getMetaData_whenTwoEvents() {
        val aggregateID = AggregateId(UUID.randomUUID())
        var myAggregate = MyAggregateRoot(aggregateID)
        val metaData = MetaData()
        metaData.set("teste", "teste")
        myAggregateRepository.save(myAggregate, metaData)
        myAggregate.clearEvents()
        assertEquals(0, myAggregate.events.size)
        var myRetrievedMetaData = myAggregateRepository.getLastMetaData(aggregateID)
        assertEquals(metaData["teste"], myRetrievedMetaData["teste"])

        myAggregate = myAggregateRepository.get(aggregateID)
        myAggregate.modify()
        metaData.set("teste", "modify")
        myAggregateRepository.save(myAggregate, metaData)
        myRetrievedMetaData = myAggregateRepository.getLastMetaData(aggregateID)
        assertEquals("modify", myRetrievedMetaData["teste"])

    }

    @Test
    fun getSavedEvents() {
        val aggregateID = AggregateId(UUID.randomUUID())
        val myAggregate = MyAggregateRoot(aggregateID)
        val dateTime = LocalDateTime.now()
        val modifyEvent = ModifyEvent("MODIFIED", dateTime)
        myAggregate.applyChange(modifyEvent)
        myAggregateRepository.save(myAggregate)

        myAggregateRepository.getSavedEvents(aggregateID).also {
            assertEquals(2, it.size)
            assertEquals(modifyEvent, it[1])
            val eventDateTime = it[1].eventPersistedDate!!.value
            val nowInUTC = ZonedDateTime.now(ZoneId.of("UTC"));
            assertTrue(eventDateTime.isWithinRange(nowInUTC.minusMinutes(5),
                    nowInUTC.plusMinutes(5)))
        }
    }

    fun ZonedDateTime.isWithinRange(startDateTime: ZonedDateTime, endDateTime: ZonedDateTime) : Boolean{
        return startDateTime.isBefore(this) && endDateTime.isAfter(this);
    }

    @Test
    fun getSavedEventsWithTenant() {
        val tenant = "tenant"
        val aggregateID = AggregateId(UUID.randomUUID(), tenant)
        val myAggregate = MyAggregateRoot(aggregateID)
        val dateTime = LocalDateTime.now()
        val modifyEvent = ModifyEvent("MODIFIED", dateTime)
        myAggregate.applyChange(modifyEvent)
        myAggregateRepository.save(myAggregate)

        myAggregateRepository.getSavedEvents(aggregateID).also {
            assertEquals(2, it.size)
            assertEquals(modifyEvent, it[1])
        }
    }

    @Test(expected = Repository.NotFoundException::class)
    fun getStreamNotFoundException() {
        val aggregateID = AggregateId(UUID.randomUUID())
        myAggregateRepository.get(aggregateID)
    }

}
