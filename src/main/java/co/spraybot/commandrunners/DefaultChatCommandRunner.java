package co.spraybot.commandrunners;

import co.spraybot.*;
import co.spraybot.ChatCommandDrone;
import co.spraybot.drones.BasicChatCommandDrone;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;

import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

public class DefaultChatCommandRunner extends AbstractVerticle implements ChatCommandRunner {

    private Map<String, ChatCommand> commands = new HashMap<>();
    private EventBus eventBus;
    private Clock clock;
    private String chatBotName;

    public DefaultChatCommandRunner(EventBus eventBus, Clock clock, String chatBotName) {
        this.eventBus = eventBus;
        this.clock = clock;
        this.chatBotName = chatBotName;
    }

    @Override
    public void registerCommand(ChatCommand chatCommand) {
        commands.put(chatCommand.getName(), chatCommand);
    }

    @Override
    public void registerCommand(String commandName, ChatCommandHandler handler) {
        ChatCommand chatCommand = new ChatCommand() {
            @Override
            public String getName() {
                return commandName;
            }

            @Override
            public Future<Void> perform(ChatCommandDrone drone) {
                return handler.handle(drone);
            }
        };
        this.registerCommand(chatCommand);
    }

    @Override
    public boolean canPerformCommand(String commandName) {
        return commands.containsKey(commandName);
    }

    @Override
    public Future<Void> performCommand(String commandName, ChatMessage chatMessage) {
        ChatCommand chatCommand = commands.get(commandName);

        if (chatCommand == null) {
            String failureMsg = "The command '" + commandName + "' is not registered in " + getClass().getName();
            return Future.failedFuture(failureMsg);
        }

        ChatCommandDrone chatCommandDrone = new BasicChatCommandDrone(eventBus, chatMessage, clock, chatBotName);
        return chatCommand.perform(chatCommandDrone);
    }

    @Override
    public void start(Future<Void> future) throws Exception {
        super.start();
        eventBus.consumer("spraybot.chatcommandrunner", msg -> {
            ChatMessage chatMessage = (ChatMessage) msg.body();
            String commandName = msg.headers().get("commandName");
            performCommand(commandName, chatMessage);
        }).completionHandler(ar -> {
            future.complete();
        });
    }
}
