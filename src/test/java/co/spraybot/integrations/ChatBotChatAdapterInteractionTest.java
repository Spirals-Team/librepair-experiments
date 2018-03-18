package co.spraybot.integrations;

import co.spraybot.ChatBot;
import co.spraybot.ChatCommandRunner;
import co.spraybot.ChatMessage;
import co.spraybot.chatbots.SprayBot;
import co.spraybot.commandrunners.DefaultChatCommandRunner;
import co.spraybot.helpers.ChatMessageProvider;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.http.WebSocket;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ChatBotChatAdapterInteractionTest extends ChatAdapterIntegrationTest implements ChatMessageProvider {

    private ChatBot chatBot;
    private ChatCommandRunner chatCommandRunner;

    @Override
    protected List<Verticle> getVerticles() {
        List<Verticle> list = super.getVerticles();
        chatBot = new SprayBot(eventBus, "testbot");
        chatCommandRunner = new DefaultChatCommandRunner(eventBus, fixedClock(), "testbot");
        list.add(chatBot);
        list.add(chatCommandRunner);
        return list;
    }

    @Test
    public void chatMessageReceivedWithFoundPattern(TestContext context) {
        Async async = context.async(5);
        chatBot.programCommand("do it", "foo_bar");
        chatCommandRunner.registerCommand("foo_bar", drone -> {
            drone.sendResponse("from the drone baby!");
            return Future.succeededFuture();
        });

        readyForWebSocketRequest("oliver").setHandler(webSocketResult -> {
            WebSocket webSocket = webSocketResult.result();
            webSocket.frameHandler(webSocketFrame -> {
                gatherFrameData(context, async, webSocketFrame, () -> {
                    List<String> expected = new ArrayList<>();
                    expected.add("Connection established!");
                    expected.add(new JsonObject().put("message", "ok").put("command", "joinChat").encode());
                    expected.add(new JsonObject()
                        .put("message", new JsonObject()
                            .put("chatMessage", new JsonObject()
                                .put("body", "do it")
                                .put("timestamp", "2015-03-02 09:00:00")
                                .put("user", new JsonObject()
                                    .put("id", "oliver")
                                    .put("name", "oliver")
                                )
                            )
                        ).put("command", "receivedChatMessage").encode()
                    );
                    expected.add(new JsonObject()
                        .put("message", new JsonObject()
                            .put("chatMessage", new JsonObject()
                                .put("body", "from the drone baby!")
                                .put("timestamp", "2015-03-02 09:00:00")
                                .put("user", new JsonObject()
                                    .put("id", "testbot")
                                    .put("name", "testbot")
                                )
                            )
                        ).put("command", "receivedChatMessage").encode()
                    );
                    context.assertEquals(expected, context.get("frames"));
                    async.complete();
                });
            });

            JsonObject joinChatRequest = new JsonObject();
            joinChatRequest.put("command", "joinChat");
            joinChatRequest.put("payload", new JsonObject());

            webSocket.writeFinalTextFrame(joinChatRequest.encode());

            JsonObject chatMessageRequest = new JsonObject();
            chatMessageRequest.put("command", "chatMessage");
            chatMessageRequest.put("payload", new JsonObject().put("userId", "oliver").put("body", "do it"));
            webSocket.writeFinalTextFrame(chatMessageRequest.encode());
        });
    }
}
