package co.spraybot.integrations;

import co.spraybot.helpers.FixedClockProvider;
import co.spraybot.messagecodecs.ChatBotResponseCodec;
import co.spraybot.messagecodecs.HardDriveSectorCodec;
import co.spraybot.messagecodecs.ChatMessageCodec;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.Timeout;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.*;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.List;

@RunWith(VertxUnitRunner.class)
public abstract class IntegrationTest implements FixedClockProvider {

    @Rule
    public RunTestOnContext contextRule = new RunTestOnContext();

    @Rule
    public Timeout timeoutRule = Timeout.millis(1_000);

    protected Vertx vertx;
    protected EventBus eventBus;

    protected abstract List<Verticle> getVerticles();

    @Before
    public void setUp(TestContext context) {
        vertx = contextRule.vertx();
        eventBus = vertx.eventBus();
        eventBus.registerCodec(new ChatMessageCodec());
        eventBus.registerCodec(new HardDriveSectorCodec());
        eventBus.registerCodec(new ChatBotResponseCodec());
        CompositeFuture deployed = deployVerticles(getVerticles());
        deployed.setHandler(context.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    private CompositeFuture deployVerticles(List<Verticle> verticles) {
        List<Future> futures = new LinkedList<>();
        for (Verticle verticle : verticles) {
            Future verticleDeploy = Future.future();
            vertx.deployVerticle(verticle, ar -> {
                if (ar.succeeded()) {
                    verticleDeploy.complete();
                } else {
                    verticleDeploy.fail(ar.cause());
                }
            });
            futures.add(verticleDeploy);
        }

        return CompositeFuture.join(futures);
    }

}
