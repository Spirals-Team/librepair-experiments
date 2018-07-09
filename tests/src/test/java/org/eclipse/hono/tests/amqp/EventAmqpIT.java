package org.eclipse.hono.tests.amqp;

import java.util.function.Consumer;

import org.apache.qpid.proton.message.Message;
import org.eclipse.hono.client.HonoClient;
import org.eclipse.hono.client.MessageConsumer;
import org.eclipse.hono.client.MessageSender;
import org.junit.runner.RunWith;

import io.vertx.core.Future;
import io.vertx.ext.unit.junit.VertxUnitRunner;

/**
 * An Event based integration test for the AMQP adapter.
 */
@RunWith(VertxUnitRunner.class)
public class EventAmqpIT extends AmqpAdapterBase {

    @Override
    protected Future<MessageConsumer> createConsumer(final String tenantId, final Consumer<Message> messageConsumer) {
        return helper.downstreamClient.createEventConsumer(tenantId, messageConsumer, remoteClose -> {
        });
    }

    @Override
    protected Future<MessageSender> getOrCreateSender(final String tenantId, final HonoClient client) {
        return client.getOrCreateEventSender(tenantId);
    }

}
