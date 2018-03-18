package co.spraybot.integrations;

import co.spraybot.ChatBot;
import co.spraybot.ChatCommandRunner;
import co.spraybot.ChatMessage;
import co.spraybot.chatbots.SprayBot;
import co.spraybot.commandrunners.DefaultChatCommandRunner;
import co.spraybot.helpers.ChatMessageProvider;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;

import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ChatBotChatCommandRunnerInteractionTest extends IntegrationTest implements ChatMessageProvider {

    private ChatBot spraybot;
    private ChatCommandRunner chatCommandRunner;

    @Override
    protected List<Verticle> getVerticles() {
        List<Verticle> list = new ArrayList<>();
        spraybot = new SprayBot(eventBus);
        chatCommandRunner = new DefaultChatCommandRunner(eventBus, fixedClock(), "spraybot");
        list.add(spraybot);
        list.add(chatCommandRunner);
        return list;
    }

    @Test
    public void sendRegisteredCommandToRunner(TestContext context) {
        Async async = context.async();
        spraybot.programCommand("foo bar", "commandName");
        chatCommandRunner.registerCommand("commandName", drone -> {
            context.assertEquals("qux", drone.getChatMessage().getHeaders().get("baz"));
            async.complete();
            return Future.succeededFuture();
        });

        ChatMessage chatMessage = fromRoomChatMessageBuilder()
            .addHeader("baz", "qux")
            .body("foo bar")
            .build();
        spraybot.chatMessageReceived(chatMessage);
    }

}
