package co.spraybot.drones;

import co.spraybot.*;
import co.spraybot.messagecodecs.ChatBotResponseCodec;
import co.spraybot.messagecodecs.HardDriveSectorCodec;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

final public class BasicChatCommandDrone implements ChatCommandDrone {

    private EventBus eventBus;
    private ChatMessage chatMessage;
    private Clock clock;
    private String chatBotName;

    public BasicChatCommandDrone(EventBus eventBus, ChatMessage usersChatMessage, Clock clock, String chatBotName) {
        this.eventBus = eventBus;
        this.chatMessage = usersChatMessage;
        this.clock = clock;
        this.chatBotName = chatBotName;
    }

    @Override
    public String getChatBotName() {
        return chatBotName;
    }

    @Override
    public ChatMessage getChatMessage() {
        return chatMessage;
    }

    @Override
    public Future<Boolean> sendResponse(String chatMessageResponse) {
        ChatBotResponse response = ChatBotResponse.builder()
            .respondingTo(chatMessage)
            .sentAt(LocalDateTime.ofInstant(clock.instant(), ZoneId.systemDefault()))
            .body(chatMessageResponse)
            .build();

        DeliveryOptions options = new DeliveryOptions();
        options.setCodecName(ChatBotResponseCodec.class.getSimpleName());

        Future<Boolean> future = Future.future();
        eventBus.send("spraybot.chatadapter", response, options, messageResult -> {
            future.complete(messageResult.succeeded());
        });

        return future;
    }

    @Override
    public Future<Boolean> sendResponseAsDirectMessage(String directMessageResponse) {
        return null;
    }

    @Override
    public Future<Boolean> storeData(String key, String value) {
        HardDriveSector hardDriveSector = new HardDriveSector() {
            @Override
            public String identifier() {
                return key;
            }

            @Override
            public String cellContents() {
                return value;
            }
        };
        Future<Boolean> future = Future.future();
        DeliveryOptions options = new DeliveryOptions();
        options.setCodecName(HardDriveSectorCodec.class.getSimpleName());
        options.addHeader("op", "write");
        eventBus.send("spraybot.harddrive", hardDriveSector, options, result -> {
            Message<Object> message = result.result();
            Boolean done = (Boolean) message.body();
            future.complete(done);
        });

        return future;
    }

    @Override
    public Future<String> fetchData(String key) {
        Future<String> future = Future.future();
        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("op", "read");
        eventBus.send("spraybot.harddrive", key, options, result -> {
            future.complete((String) result.result().body());
        });
        return future;
    }

    @Override
    public Future<Void> destroyData(String key) {
        Future<Void> future = Future.future();

        DeliveryOptions options =new DeliveryOptions();
        options.addHeader("op", "erase");
        eventBus.send("spraybot.harddrive", key, options, result -> {
            future.complete();
        });

        return future;
    }

    @Override
    public Future<Void> destroyAllData() {
        Future<Void> future = Future.future();

        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("op", "eraseEverything");
        eventBus.send("spraybot.harddrive", null, options, result -> {
            future.complete();
        });

        return future;
    }

}
