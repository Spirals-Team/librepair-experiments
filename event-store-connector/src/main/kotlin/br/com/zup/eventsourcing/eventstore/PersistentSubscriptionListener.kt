package br.com.zup.eventsourcing.eventstore

import akka.actor.*
import akka.event.Logging
import br.com.zup.eventsourcing.core.*
import br.com.zup.eventsourcing.core.config.jsonToObject
import eventstore.EventStream
import eventstore.PersistentSubscriptionActor
import eventstore.ResolvedEvent
import eventstore.Settings
import java.nio.charset.Charset
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime


class PersistentSubscriptionListener(val eventHandler: EventStoreEventHandler,
                                     subscriptionGroupName: String,
                                     val subscriptionName: String,
                                     conneciton: ActorRef) : AbstractLoggingActor() {
    val actorSystem = ActorSystem.create()!!
    val child: ActorRef

    init {

        child = context.actorOf(PersistentSubscriptionActor.props(conneciton,
                self, EventStream.`Id$`.`MODULE$`.apply(subscriptionName),
                getGroupName(subscriptionGroupName), Settings.Default().defaultCredentials(), Settings.Default(), false))

        context.watch(child)
    }

    companion object {
        fun props(eventHandler: EventStoreEventHandler, subscriptionGroupName: String, subscriptionName: String, connection: ActorRef): Props {
            return Props.create(PersistentSubscriptionListener::class.java) {
                PersistentSubscriptionListener(eventHandler, subscriptionGroupName, subscriptionName,connection)
            }
        }
    }


    private val log = Logging.getLogger(context.system, this)

    override fun createReceive(): Receive {
        return receiveBuilder()
                .match(Props::class.java, {
                    sender.tell(context.actorOf(it), self)
                })
                .match(ResolvedEvent::class.java, {
                    run {
                        log.info("Received ResolvedEvent message: {}", it)
                        onReceive(it)
                    }
                })
                .build()
    }

    override fun supervisorStrategy(): SupervisorStrategy {
        return super.supervisorStrategy()
    }

    fun onReceive(resolvedEvent: ResolvedEvent) {
        try {
            val obj = tryToSerialize(resolvedEvent)
            val metaData = getEventMetaData(resolvedEvent).jsonToObject(MetaData::class.java)
            val created = getPersistedDate(resolvedEvent)
            val event = obj as Event
            val aggregateId = getAggregateId(resolvedEvent, event)
            event.eventPersistedDate = created
            eventHandler.handle(aggregateId, event, metaData, AggregateVersion(resolvedEvent.linkedEvent().number().value()))
            sender.tell(PersistentSubscriptionActor.ManualAck(resolvedEvent.linkEvent().data().eventId()), self)
        } catch (e: Exception){
            log.error(e, "retrying event: $resolvedEvent")
            sender.tell(PersistentSubscriptionActor.ManualNak(resolvedEvent.linkEvent().data().eventId()), self)
        }

    }

    private fun getPersistedDate(resolvedEvent: ResolvedEvent): EventPersistedDate {
        val instant = Instant.ofEpochMilli(resolvedEvent.created().get().toInstant().millis)
        val utcZoneId = ZoneId.of("UTC")
        return EventPersistedDate(ZonedDateTime.ofInstant(instant, utcZoneId))
    }

    private fun tryToSerialize(resolvedEvent: ResolvedEvent) : Any {
        try{
            return getEventData(resolvedEvent).jsonToObject(Class.forName(getEventDataClassName(resolvedEvent)))
        } catch (e: ClassNotFoundException){
            return getEventData(resolvedEvent).jsonToObject(Event::class.java)
        }

    }


    private fun getAggregateId(resolvedEvent: ResolvedEvent, event: Event): AggregateId {
        return eventHandler.getAggregateId(resolvedEvent.linkedEvent().streamId().value(), event)
    }

    private fun getEventMetaData(message: ResolvedEvent) = message.linkedEvent().data().metadata().value().decodeString(Charset
            .defaultCharset())

    private fun getEventDataClassName(message: ResolvedEvent) = message.data().eventType()

    private fun getEventData(message: ResolvedEvent) = message.linkedEvent().data().data().value().decodeString(Charset
            .defaultCharset())

    private fun getGroupName(subscriptionGroupName: String): String? {
        if (subscriptionGroupName.isBlank()) {
            return subscriptionName + "SubscriptionGroup"
        } else {
            return subscriptionGroupName
        }
    }


}
