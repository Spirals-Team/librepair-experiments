package br.com.zup.eventsourcing.core

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY
import org.apache.logging.log4j.LogManager
import java.util.*

abstract class AggregateRoot : Cloneable {
    private val LOG = LogManager.getLogger(this.javaClass)

    lateinit var id: AggregateId

    var version: AggregateVersion = AggregateVersion(-1)
    var event: Event? = null
    var events: MutableList<Event> = ArrayList()

    abstract fun applyEvent(event: Event)

    fun load(events: List<Event>, aggregateVersion: AggregateVersion): AggregateRoot {
        for (event: Event in events) {
            applyChangeWithoutStackingEvents(event)
        }
        version = aggregateVersion
        return this
    }

    fun applyChange(event: Event) {
        LOG.debug("Applying event: {}", event)
        this.event = event
        this.events.add(event)

        applyEvent(event)
    }

    fun clearEvents() {
        events.clear()
    }

    private fun applyChangeWithoutStackingEvents(event: Event) {
        applyEvent(event)
    }

    fun copyWithVersion(version: AggregateVersion): AggregateRoot {
        val newAggregate = (this.clone() as AggregateRoot)
        newAggregate.events = ArrayList()
        newAggregate.version = version
        newAggregate.event = null
        return newAggregate
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as AggregateRoot

        if (!id.equals(other.id)) return false

        return true
    }
}

data class AggregateVersion(val value: Int)

open class AggregateId @JvmOverloads constructor(
    val value: String,
    @JsonIgnoreProperties(ignoreUnknown = true) @JsonInclude(NON_EMPTY) val tenant: String? = ""
) {

    @JvmOverloads
    constructor(value: UUID, tenant: String? = "") : this(value.toString(), tenant)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AggregateId

        if (value != other.value) return false
        if (tenant != other.tenant) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + (tenant?.hashCode() ?: 0)
        return result
    }

    override fun toString() = value
}

