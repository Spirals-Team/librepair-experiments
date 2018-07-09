package org.eclipse.hono.tests.amqp;

import java.net.HttpURLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.apache.qpid.proton.message.Message;
import org.eclipse.hono.client.ClientErrorException;
import org.eclipse.hono.client.HonoClient;
import org.eclipse.hono.client.MessageConsumer;
import org.eclipse.hono.client.MessageSender;
import org.eclipse.hono.client.impl.HonoClientImpl;
import org.eclipse.hono.config.ClientConfigProperties;
import org.eclipse.hono.tests.IntegrationTestSupport;
import org.eclipse.hono.util.Constants;
import org.eclipse.hono.util.TenantObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.proton.ProtonClientOptions;
import io.vertx.proton.ProtonHelper;

/**
 * Base class for the AMQP Adapter integration tests.
 */
public abstract class AmqpAdapterBase {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private static final int MESSAGES_TO_SEND = 200;

    private static final int TEST_TIMEOUT = 10000;

    @Rule
    public final Timeout timeout = new Timeout(TEST_TIMEOUT, TimeUnit.MILLISECONDS);

    @Rule
    public final TestName testName = new TestName();

    /**
     * The Vert.x instance to run on.
     */
    private static Vertx vertx;

    private static final Set<HonoClient> clientsToShutdown = new HashSet<>();

    /**
     * A helper for accessing the AMQP 1.0 messaging network and for managing devices tenants, devices and credentials.
     */
    protected static IntegrationTestSupport helper;

    protected abstract Future<MessageConsumer> createConsumer(String tenantId, Consumer<Message> messageConsumer);

    protected abstract Future<MessageSender> getOrCreateSender(String tenantId, HonoClient client);

    /**
     * Sets up and initializes the integration test setup.
     * 
     * @param context The Vert.x test context.
     */
    @BeforeClass
    public static void init(final TestContext context) {
        vertx = Vertx.vertx();
        helper = new IntegrationTestSupport(vertx);
        helper.init(context);
    }

    /**
     * Shut down the client and close the AMQP 1.0 messaging network client.
     * 
     * @param context The Vert.x test context.
     */
    @AfterClass
    public static void shutdown(final TestContext context) {
        shutdownClients(context, vertx);
        helper.disconnect(context);
    }

    /**
     * Logs message before running a test case.
     */
    @Before
    public void setUp() {
        LOG.info("running {}", testName.getMethodName());
    }

    /**
     * Deletes all temporary objects from the device registry.
     * 
     * @param context The Vert.x test context.
     */
    @After
    public void postTest(final TestContext context) {
        helper.deleteObjects(context);
    }

    /**
     * Verifies that a BAD inbound message (i.e when the message content-type does not match the payload) is rejected by
     * the adapter. This test case uses the credentials on record for device 4711 of the default tenant.
     * 
     * @param context The Vert.x context for running asynchronous tests.
     */
    @Test
    public void testAdapterRejectsBadInboundMessage(final TestContext context) {
        final Async completionTracker = context.async();

        final String username = IntegrationTestSupport.getUsername("sensor1", Constants.DEFAULT_TENANT);
        final String password = "hono-secret";
        final HonoClient client = prepareAmqpAdapterClient(username, password);
        client.connect(new ProtonClientOptions())
                .compose(ok -> {
                    clientsToShutdown.add(client);
                    return getOrCreateSender(Constants.DEFAULT_TENANT, client);
                })
                .compose(sender -> {
                    final Message msg = ProtonHelper.message("msg payload");
                    msg.setContentType("application/vnd.eclipse-hono-empty-notification");
                    return sender.send(msg);
                })
                .setHandler(context.asyncAssertFailure(t -> {
                    context.assertTrue(ClientErrorException.class.isInstance(t));
                    context.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, ((ClientErrorException) t).getErrorCode());
                    completionTracker.complete();
                }));
        completionTracker.awaitSuccess();
    }

    /**
     * Verifies that the AMQP adapter rejects messages from a device that belongs to a tenant for which it has been
     * disabled. This test case only considers events since telemetry senders do not wait for response from the adapter.
     * 
     * @param context The Vert.x test context.
     */
    @Test
    public void testUploadMessageFailsForTenantDisabledAdapter(final TestContext context) {
        final Async completionTracker = context.async();

        final String tenantId = helper.getRandomTenantId();
        final String deviceId = helper.getRandomDeviceId(tenantId);
        final String username = IntegrationTestSupport.getUsername(deviceId, tenantId);
        final String password = "secret";

        final TenantObject tenant = TenantObject.from(tenantId, true);
        tenant.addAdapterConfiguration(TenantObject.newAdapterConfig(Constants.PROTOCOL_ADAPTER_TYPE_AMQP, false));

        final Async addTenantTracker = context.async();

        helper.registry
                .addDeviceForTenant(tenant, deviceId, password)
                .setHandler(context.asyncAssertSuccess(ok -> addTenantTracker.complete()));

        addTenantTracker.await();

        final HonoClient client = prepareAmqpAdapterClient(username, password);
        client.connect(new ProtonClientOptions()).compose(ok -> {
            clientsToShutdown.add(client);
            return getOrCreateSender(tenantId, client);
        }).compose(sender -> {
            final Message msg = ProtonHelper.message("some payload");
            return sender.send(msg);
        }).setHandler(context.asyncAssertFailure(t -> {
            context.assertTrue(ClientErrorException.class.isInstance(t));

            // HonoClient maps AmqpError.UNAUTHORIZED_ACCESS to HttpURLConnection.HTTP_BAD_REQUEST
            // -> so we test the message

            final String expectedErrorMsg = String
                    .format("This adapter is not enabled for tenant [tenantId: %s].", tenantId);

            context.assertEquals(expectedErrorMsg, ((ClientErrorException) t).getMessage());

            completionTracker.complete();
        }));
        completionTracker.awaitSuccess();
    }

    /**
     * Verifies that a number of messages published through the AMQP adapter can be successfully consumed by
     * applications connected to the AMQP messaging network.
     *
     * @param context The Vert.x test context.
     * @throws InterruptedException Exception.
     */
    @Test
    public void testUploadingXnumberOfMessages(final TestContext context) throws InterruptedException {

        final String tenantId = helper.getRandomTenantId();
        final String deviceId = helper.getRandomDeviceId(tenantId);
        final String username = IntegrationTestSupport.getUsername(deviceId, tenantId);
        final String password = "verysecret";

        final CountDownLatch received = new CountDownLatch(MESSAGES_TO_SEND);

        final Async addTenantTracker = context.async();
        final TenantObject tenant = TenantObject.from(tenantId, true);
        helper.registry.addDeviceForTenant(tenant, deviceId, password)
                .setHandler(context.asyncAssertSuccess(ok -> addTenantTracker.complete()));
        addTenantTracker.await();

        final Async setup = context.async();

        final HonoClient client = prepareAmqpAdapterClient(username, password);
        client.connect(new ProtonClientOptions()).compose(ok -> {
            clientsToShutdown.add(client);
            return Future.succeededFuture();
        }).compose(ok -> createConsumer(tenantId, msg -> {
            received.countDown();
            if (received.getCount() % 40 == 0) {
                LOG.info("message {} received", MESSAGES_TO_SEND - received.getCount());
            }
        })).setHandler(context.asyncAssertSuccess(ok -> setup.complete()));
        setup.await();

        final AtomicInteger messageCount = new AtomicInteger(0);

        while (messageCount.get() < MESSAGES_TO_SEND) {
            final Async sendingComplete = context.async();
            getOrCreateSender(tenantId, client).compose(sender -> {
                final Message message = ProtonHelper.message("temp: " + messageCount.getAndIncrement());
                sender.send(message).setHandler(sendOutcome -> {
                    if (sendOutcome.succeeded()) {
                        if (messageCount.get() % 40 == 0) {
                            LOG.info("messages sent: {}", messageCount.get());
                        }
                    } else {
                        LOG.debug("error sending message {}", messageCount.get(), sendOutcome.cause());
                    }
                    sendingComplete.complete();
                });

                return Future.succeededFuture();
            });
            sendingComplete.await();
        }

        final long timeToWait = Math.max(TEST_TIMEOUT - 1000, Math.round(MESSAGES_TO_SEND * 20));
        received.await(timeToWait, TimeUnit.MILLISECONDS);
        final long messagesReceived = MESSAGES_TO_SEND - received.getCount();
        LOG.info("sent {} and received {} messages", messageCount, messagesReceived);
        if (messagesReceived < messageCount.get()) {
            context.fail("did not receive all messages");
        }
    }

    // ------------------------------< private methods >---

    private static HonoClient prepareAmqpAdapterClient(final String username, final String password) {
        final ClientConfigProperties config = new ClientConfigProperties();
        config.setName("test");
        config.setHost(IntegrationTestSupport.AMQP_HOST);
        config.setPort(IntegrationTestSupport.AMQP_PORT);
        if (username != null && password != null) {
            config.setUsername(username);
            config.setPassword(password);
        }
        return new HonoClientImpl(vertx, config);
    }

    private static void shutdownClients(final TestContext context, final Vertx vertx) {
        clientsToShutdown.forEach(client -> {
            final Async shutdown = context.async();
            final Future<Void> shutdownTracker = Future.future();

            client.shutdown(shutdownTracker.completer());
            shutdownTracker.setHandler(ok -> shutdown.complete());
            shutdown.await();
        });

        final Future<Void> vertxTracker = Future.future();
        vertx.close(vertxTracker.completer());
        vertxTracker.setHandler(context.asyncAssertSuccess());
    }
}
