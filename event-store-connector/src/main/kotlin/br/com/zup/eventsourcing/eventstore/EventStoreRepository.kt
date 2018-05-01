package br.com.zup.eventsourcing.eventstore

import akka.actor.ActorSystem
import akka.actor.Status
import akka.util.Timeout
import br.com.zup.eventsourcing.core.*
import br.com.zup.eventsourcing.core.Event
import br.com.zup.eventsourcing.core.config.jsonToObject
import br.com.zup.eventsourcing.core.config.objectToJson
import br.com.zup.eventsourcing.core.util.ReflectHelper
import eventstore.*
import eventstore.j.EsConnection
import eventstore.j.EsConnectionFactory
import eventstore.j.EventDataBuilder
import org.apache.logging.log4j.LogManager
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import java.nio.charset.Charset
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime


abstract class EventStoreRepository<T : AggregateRoot> : Repository<T>() {
    val actorSystem = ActorSystem.create()!!
    val esConnection: EsConnection = EsConnectionFactory.create(actorSystem)
    private val log = LogManager.getLogger(this.javaClass)

    open fun getStreamName(aggregateId: AggregateId) =
        if (aggregateId.tenant.isNullOrEmpty())
            "${getGenericName()}-${aggregateId.value}"
        else
            "${getGenericName()}-${aggregateId.tenant}-${aggregateId.value}"

    override fun save(aggregateRoot: AggregateRoot, lock: OptimisticLock): T =
        save(aggregateRoot, MetaData(), lock)

    override fun save(aggregateRoot: AggregateRoot, metaData: MetaData, lock: OptimisticLock): T {
        log.debug("received save message with aggregate: $aggregateRoot and meta data: $metaData")
        try {
            return saveEventsSynchronously(aggregateRoot, metaData, lock)
                .also { log.debug("aggregate saved: $aggregateRoot and meta data: $metaData") }
        } catch (e: Exception) {
            log.error("error saving aggregate: $aggregateRoot and meta data: $metaData", e)
            throw e
        }

    }

    private fun saveEventsSynchronously(aggregate: AggregateRoot, metaData: MetaData, lock: OptimisticLock): T {
        if (aggregate.events.size > 0) {
            val timeout = Timeout(Duration.create(60, "seconds"))
            val items = aggregate.events.map { event ->
                EventDataBuilder(event.retrieveEventType().value)
                    .eventId(event.id.value)
                    .jsonData(event.retrieveJsonData().data)
                    .jsonMetadata(metaData.objectToJson())
                    .build()
            }
            val future: Future<WriteResult> = esConnection.writeEvents(
                getStreamName(aggregate.id),
                getExpectedVersion(aggregate.version.value, lock),
                items,
                null,
                false
            )
            val message = Await.result(future, timeout.duration())
            validateSaveMessageResult(aggregate, message)
        }
        return aggregate.copyWithVersion(AggregateVersion(aggregate.version.value + aggregate.events.size)) as T
    }

    private fun getExpectedVersion(expectedVersion: Int, lock: OptimisticLock): ExpectedVersion? {
        return when (lock) {
            OptimisticLock.ENABLED -> {
                if (expectedVersion == -1)
                    ExpectedVersion.`NoStream$`.`MODULE$`
                else
                    ExpectedVersion.Exact(expectedVersion)
            }
            OptimisticLock.DISABLED -> {
                ExpectedVersion.`Any$`.`MODULE$`
            }
        }
    }

    private fun validateSaveMessageResult(aggregate: AggregateRoot, message: Any?) {
        if (message == null) {
            val aggregateGot = get(aggregate.id)
            if (aggregateGot.version.value == aggregate.version.value + 1) {
                log.warn("is null, but we checked, the message is there, better look if server its ok: ")
            } else {
                log.warn("is null, and I dont know why so check if server its ok: ")
                throw InternalError()
            }
        } else if (message is WriteResult) {
            log.debug("on WriteResult: " + message.toString())
        } else if (message is Status.Failure) {
            log.error("on Status.Failure: " + message.toString())
            throw Exception(message.toString())
        } else {
            log.error("on Failure: " + message.toString())
            throw Exception(message.toString())
        }
    }

    override fun get(aggregateId: AggregateId): T {
        log.debug("receive get message with aggregateId: $aggregateId")
        val message = readEventsFromBeginningWithError(aggregateId)
        log.debug("receive get message with aggregateId: $aggregateId and message $message")
        return replayAggregateRoot(message)

    }

    override fun find(aggregateId: AggregateId): T? {
        log.debug("receive find message with aggregateId: $aggregateId")
        val message = readEventsFromBeginning(aggregateId)
        log.debug("receive find message with aggregateId: $aggregateId and message $message")
        return message?.let { replayAggregateRoot(message) }

    }

    override fun getSavedEvents(aggregateId: AggregateId): List<Event> {
        val message = readEventsFromBeginningWithError(aggregateId)
        return getEventsFromStream(message)
    }

    private fun getEventsFromStream(message: ReadStreamEventsCompleted): ArrayList<Event> {
        val events = ArrayList<Event>()
        for (event: eventstore.Event in message.events()) {
            val obj = tryToSerialize(event)
            val created = getPersistedDate(event)
            obj as Event
            obj.eventPersistedDate = created
            events.add(obj)
        }
        return events
    }

    private fun tryToSerialize(event: eventstore.Event): Any {
        try {
            return getEventData(event).jsonToObject(Class.forName(event.data().eventType()))
        } catch (e: ClassNotFoundException) {
            return getEventData(event).jsonToObject(Event::class.java)
        }

    }

    private fun getEventData(event: eventstore.Event) : String =
        event.record().data().data().value().decodeString(Charset.defaultCharset())

    private fun getPersistedDate(event: eventstore.Event): EventPersistedDate {
        val instant = Instant.ofEpochMilli(event.created().get().toInstant().millis)
        val utcZoneId = ZoneId.of("UTC")
        return EventPersistedDate(ZonedDateTime.ofInstant(instant,utcZoneId))
    }


    private fun replayAggregateRoot(readStreamEventsCompleted: ReadStreamEventsCompleted): T {
        val streamEvents = readStreamEventsCompleted.events()
        val version = if (streamEvents.isEmpty) -1 else streamEvents.last().number().value()
        val savedEvents = getEventsFromStream(readStreamEventsCompleted)
        val aggregateClass: Class<*> = Class.forName(getGenericCanonicalName())

        if (!savedEvents.isEmpty()) {
            val constructor = ReflectHelper.getDefaultNoArgsConstructor(aggregateClass)
            val aggregate = constructor.newInstance()
            (aggregate as T).load(savedEvents, AggregateVersion(version))
            return aggregate
        } else {
            log.error("stream was empty: $readStreamEventsCompleted")
            throw NotFoundException()
        }
    }

    override fun getLastMetaData(aggregateId: AggregateId): MetaData {
        log.debug("receive get meta data message with aggregateId: $aggregateId")
        val message = readEventsFromBeginningWithError(aggregateId)
        return getMetaDataFromLastEvent(message)

    }

    private fun getMetaDataFromLastEvent(readStreamEventsCompleted: ReadStreamEventsCompleted): MetaData {
        val event = readStreamEventsCompleted.events().last()
        return event.data().metadata().value().decodeString(Charset.defaultCharset()).jsonToObject(
            Class.forName
                (MetaData::class.qualifiedName)
        ) as MetaData
    }

    private fun readEventsFromBeginningWithError(aggregateId: AggregateId): ReadStreamEventsCompleted {
        try {
            return tryReadEventsFromBeginning(aggregateId)
        } catch (e: StreamNotFoundException) {
            log.warn("there is no aggregate with aggregateId: $aggregateId", e)
            throw NotFoundException()
        } catch (e: Exception) {
            log.error("was not able to find and aggregate with aggregateId: $aggregateId", e)
            throw e
        }
    }

    private fun readEventsFromBeginning(aggregateId: AggregateId): ReadStreamEventsCompleted? {
        return try {
             tryReadEventsFromBeginning(aggregateId)
        } catch (e: StreamNotFoundException) {
            log.warn("there is no aggregate with aggregateId: $aggregateId", e)
            null
        } catch (e: Exception) {
            log.error("was not able to find and aggregate with aggregateId: $aggregateId", e)
            throw e
        }
    }

    private fun tryReadEventsFromBeginning(aggregateId: AggregateId): ReadStreamEventsCompleted {
        val timeout = Timeout(Duration.create(60, "seconds"))
        val future = esConnection.readStreamEventsForward(
            getStreamName(aggregateId), EventNumber.Exact(0),
            4000,
            true,
            null
        )
        val message = Await.result(future, timeout.duration())
        return validatedReadStreamEventsCompleted(message)
    }

    private fun validatedReadStreamEventsCompleted(readStreamEventsCompleted: ReadStreamEventsCompleted?):
        ReadStreamEventsCompleted {
        if (readStreamEventsCompleted is ReadStreamEventsCompleted) {
            return readStreamEventsCompleted
        } else {
            throw MessageNotExpectedException()
        }
    }

    class MessageNotExpectedException : RuntimeException()
}