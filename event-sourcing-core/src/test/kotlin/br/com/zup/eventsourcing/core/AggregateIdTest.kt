package br.com.zup.eventsourcing.core

import br.com.zup.eventsourcing.core.domain.ModifyEvent
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals

class AggregateIdTest {
    @Test
    fun equals_withSameObject() {
        val myAggregateIdFirst = AggregateId(UUID.randomUUID(), "someTenant")
        val myAggregateIdSecond = myAggregateIdFirst
        assertTrue(myAggregateIdFirst == myAggregateIdSecond)
    }

    @Test
    fun equals_withDifferentClasses() {
        val uuid = UUID.randomUUID()
        val tenant = "someTenant"
        val myAggregateIdFirst = AggregateId(uuid, tenant)
        val myEvent = ModifyEvent("Modified", LocalDateTime.now())
        assertFalse(myAggregateIdFirst.equals(myEvent))
    }

    @Test
    fun equals_withSameAggregateIdValueAndTenant() {
        val uuid = UUID.randomUUID()
        val tenant = "someTenant"
        val myAggregateIdFirst = AggregateId(uuid, tenant)
        val myAggregateIdSecond = AggregateId(uuid, tenant)
        assertTrue(myAggregateIdFirst == myAggregateIdSecond)
    }

    @Test
    fun equals_withDifferentAggregateIdValue() {
        val tenant = "someTenant"
        val myAggregateIdFirst = AggregateId(UUID.randomUUID(), tenant)
        val myAggregateIdSecond = AggregateId(UUID.randomUUID(), tenant)
        assertFalse(myAggregateIdFirst == myAggregateIdSecond)
    }

    @Test
    fun equals_withDifferentTenant() {
        val uuid = UUID.randomUUID()
        val tenant = "someTenant"
        val tenant2 = "someTenant2"
        val myAggregateIdFirst = AggregateId(uuid, tenant)
        val myAggregateIdSecond = AggregateId(uuid, tenant2)
        assertFalse(myAggregateIdFirst == myAggregateIdSecond)
    }

    @Test
    fun hashCode_sameObject() {
        val myAggregateIdFirst = AggregateId(UUID.randomUUID(), "someTenant")
        val myAggregateIdSecond = myAggregateIdFirst
        assertTrue(myAggregateIdFirst.hashCode() == myAggregateIdSecond.hashCode())
    }

    @Test
    fun hashCode_withSameAggregateIdValueAndTenant() {
        val uuid = UUID.randomUUID()
        val tenant = "someTenant"
        val myAggregateIdFirst = AggregateId(uuid,  tenant)
        val myAggregateIdSecond = AggregateId(uuid, tenant)
        assertTrue(myAggregateIdFirst.hashCode() == myAggregateIdSecond.hashCode())
    }

    @Test
    fun hashCode_withDifferentAggregateIdValue() {
        val tenant = "someTenant"
        val myAggregateIdFirst = AggregateId(UUID.randomUUID(), tenant)
        val myAggregateIdSecond = AggregateId(UUID.randomUUID(), tenant)
        assertFalse(myAggregateIdFirst.hashCode() == myAggregateIdSecond.hashCode())
    }

    @Test
    fun hashCode_withDifferentAggregateIdTenant() {
        val uuid = UUID.randomUUID()
        val tenant = "someTenant"
        val tenant2 = "someTenant2"
        val myAggregateIdFirst = AggregateId(uuid, tenant)
        val myAggregateIdSecond = AggregateId(uuid, tenant2)
        assertFalse(myAggregateIdFirst.hashCode() == myAggregateIdSecond.hashCode())
    }

    @Test
    fun aggregateIdString() {
        val uuid = UUID.randomUUID().toString()
        val myAggregateId = AggregateId(uuid)
        assertEquals(uuid, myAggregateId.value)
        assertTrue(myAggregateId.tenant!!.isEmpty())
    }

    @Test
    fun aggregateIdStringWithTenant() {
        val uuid = UUID.randomUUID().toString()
        val tenant = "someTenant"
        val myAggregateId = AggregateId(uuid, tenant)
        assertEquals(uuid, myAggregateId.value)
        assertEquals(tenant, myAggregateId.tenant)
    }

}