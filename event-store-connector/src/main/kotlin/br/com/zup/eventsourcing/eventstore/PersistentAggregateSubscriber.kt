package br.com.zup.eventsourcing.eventstore

import akka.actor.ActorSystem
import akka.pattern.Backoff
import akka.pattern.BackoffSupervisor
import br.com.zup.eventsourcing.core.AggregateRoot
import com.typesafe.config.ConfigFactory
import eventstore.tcp.ConnectionActor
import scala.concurrent.duration.Duration
import java.lang.reflect.ParameterizedType
import java.util.concurrent.TimeUnit

abstract class PersistentAggregateSubscriber<T : AggregateRoot>(val subscriptionGroupName: String = "",
                                                                val eventHandler: EventStoreEventHandler) {

    val actorSystem = ActorSystem.create()!!

    open fun start() {

        val config = ConfigFactory.load()

        val minBackoff = config.getLong("persistent.subscription.min.backoff")
        val maxBackoff = config.getLong("persistent.subscription.max.backoff")
        val randomFactor = config.getDouble("persistent.subscription.random.factor")

        val connection = actorSystem.actorOf(ConnectionActor.getProps())

        val supervisorProps = BackoffSupervisor.props(
                Backoff.onStop(
                        PersistentSubscriptionListener.props(eventHandler, subscriptionGroupName, getSubscriptionName(), connection),
                        "persistentSubscriptionListener" + getSubscriptionName(),
                        Duration.create(minBackoff, TimeUnit.SECONDS),
                        Duration.create(maxBackoff, TimeUnit.SECONDS),
                        randomFactor
                )
        )

        actorSystem.actorOf(supervisorProps)
    }

    protected open fun getSubscriptionName(): String {
        return ((javaClass
                .genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>).simpleName
    }
}
