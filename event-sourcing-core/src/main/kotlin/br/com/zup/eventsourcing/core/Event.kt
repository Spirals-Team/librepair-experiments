package  br.com.zup.eventsourcing.core

import br.com.zup.eventsourcing.core.config.objectToJson
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.ZonedDateTime
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
open class Event(val id: EventID = EventID(),
                 var eventPersistedDate: EventPersistedDate? = null,
                 var eventAggregateId: AggregateId? = null) {
    fun retrieveEventType(): EventType = EventType(this.javaClass.canonicalName)
    fun retrieveJsonData(): JsonData = JsonData(this.objectToJson())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}

class EventID(val value: UUID = UUID.randomUUID()) {
    override fun equals(other: Any?): Boolean = when {
        this === other -> true
        other === null || other !is EventID -> false
        this::class != other::class -> false
        else -> value == other.value
    }

    override fun hashCode(): Int = value.hashCode()
}

class JsonData(val data: String)
class EventType(val value: String)
class EventPersistedDate(val value: ZonedDateTime)
