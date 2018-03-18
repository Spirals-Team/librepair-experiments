package co.spraybot.integrations;

import co.spraybot.ChatCommandDrone;
import co.spraybot.ChatMessage;
import co.spraybot.drones.BasicChatCommandDrone;
import co.spraybot.harddrive.TransientHardDrive;
import co.spraybot.helpers.ChatMessageProvider;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;

import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ChatCommandDroneHardDriveInteractionTest extends IntegrationTest implements ChatMessageProvider {

    private ChatCommandDrone subject;

    @Override
    protected List<Verticle> getVerticles() {
        List<Verticle> list = new ArrayList<>();
        list.add(new TransientHardDrive(eventBus));
        return list;
    }

    @Before
    public void setUp(TestContext context) {
        super.setUp(context);
        ChatMessage chatMessage = fromRoomChatMessage();
        subject = new BasicChatCommandDrone(eventBus, chatMessage, fixedClock(), "spraybot");
    }

    @Test
    public void storingAndFetchingData(TestContext context) {
        Async async = context.async();
        Future<Boolean> learned = subject.storeData("id", "bar");
        learned.setHandler(result -> {
            context.assertTrue(result.succeeded());
            context.assertTrue(result.result());

            Future<String> remembered = subject.fetchData("id");
            remembered.setHandler(fetchResult -> {
                context.assertTrue(fetchResult.succeeded());
                context.assertEquals("bar", fetchResult.result());
                async.complete();
            });
        });
    }

    @Test
    public void storingAndForgettingSpecificData(TestContext context) {
        Async async = context.async();

        Future<Boolean> storedFoo = subject.storeData("foo", "bar");
        Future<Boolean> storedBar = subject.storeData("bar", "baz");
        Future<Boolean> storedBaz = subject.storeData("baz", "qux");

        CompositeFuture.join(storedFoo, storedBar, storedBaz).setHandler(result -> {
            context.assertTrue(result.succeeded());
            CompositeFuture resultData = result.result();
            context.assertTrue(resultData.resultAt(0));
            context.assertTrue(resultData.resultAt(1));
            context.assertTrue(resultData.resultAt(2));

            Future<String> rememberedFoo = subject.fetchData("foo");
            Future<String> rememberedBar = subject.destroyData("bar").compose(forgotResult -> subject.fetchData("bar"));
            Future<String> rememberedBaz = subject.fetchData("baz");

            CompositeFuture.join(rememberedFoo, rememberedBar, rememberedBaz).setHandler(remembered -> {
                context.assertTrue(remembered.succeeded());
                CompositeFuture rememberedData = remembered.result();
                context.assertEquals("bar", rememberedData.resultAt(0));
                context.assertNull(rememberedData.resultAt(1));
                context.assertEquals("qux", rememberedData.resultAt(2));
                async.complete();
            });
        });
    }

    @Test
    public void forgettingEverything(TestContext context) {
        Async async = context.async();

        Future<Boolean> storedFoo = subject.storeData("foo", "bar");
        Future<Boolean> storedBar = subject.storeData("bar", "baz");
        Future<Boolean> storedBaz = subject.storeData("baz", "qux");

        CompositeFuture.join(storedFoo, storedBar, storedBaz).setHandler(result -> {
            context.assertTrue(result.succeeded());
            CompositeFuture resultData = result.result();
            context.assertTrue(resultData.resultAt(0));
            context.assertTrue(resultData.resultAt(1));
            context.assertTrue(resultData.resultAt(2));

            Future<Void> forgotEverything = subject.destroyAllData();
            Future<String> rememberedFoo = subject.fetchData("foo");
            Future<String> rememberedBar = subject.fetchData("bar");
            Future<String> rememberedBaz = subject.fetchData("baz");
            CompositeFuture.join(forgotEverything, rememberedFoo, rememberedBar, rememberedBaz).setHandler(remembered -> {
                context.assertTrue(remembered.succeeded());
                CompositeFuture rememberedData = remembered.result();
                context.assertNull(rememberedData.resultAt(1));
                context.assertNull(rememberedData.resultAt(2));
                context.assertNull(rememberedData.resultAt(3));
                async.complete();
            });
        });
    }
}
